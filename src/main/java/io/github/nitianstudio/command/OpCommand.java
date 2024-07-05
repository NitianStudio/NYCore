package io.github.nitianstudio.command;

import io.github.nitianstudio.command.permissions.Permissions;
import io.github.nitianstudio.config.Op;
import lombok.val;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.*;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentUUID;
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentRegistry;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

import static io.github.nitianstudio.command.permissions.Permissions.op;
import static io.github.nitianstudio.config.Op.ops;
import static io.github.nitianstudio.server.NycImpl.commandManager;
import static io.github.nitianstudio.server.NycImpl.connManager;

public class OpCommand extends Command {

    private static final ArgumentEntity player = ArgumentType.Entity("player").onlyPlayers(true);
    public OpCommand() {
        super("op");

        player.setCallback(this::callback);
//        setDefaultExecutor((sender, ctx) -> {
//
//        });
        addSyntax(this::executor, player);
    }

    private void executor(CommandSender sender, CommandContext ctx) {
        if (sender instanceof Player user && user.hasPermission(op.getPermission())) {
            val players = ctx.get(player).find(commandManager().getConsoleSender());
            for (var entity : players) {
                if (entity instanceof Player p) {
                    Op.put(p.getUsername(), p.getUuid());
                }
                Op.save();

            }
        }
    }

    private void callback(CommandSender sender, ArgumentSyntaxException e) {

    }
}
