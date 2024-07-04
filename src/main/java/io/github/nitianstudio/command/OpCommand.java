package io.github.nitianstudio.command;

import io.github.nitianstudio.config.Op;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.*;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentRegistry;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Player;
import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OpCommand extends Command {
    Permission op = new Permission("minecraft.op");
    private static final ArgumentEntity player = ArgumentType.Entity("player").onlyPlayers(true);
    public OpCommand(@NotNull String name, @Nullable String... aliases) {
        super(name, aliases);

        player.setCallback(this::callback);
//        setDefaultExecutor((sender, ctx) -> {
//
//        });
        addSyntax(this::executor, player);
    }

    private void executor(CommandSender sender, CommandContext ctx) {
        if (sender instanceof ServerSender) {
            if (ctx.get(player).find(sender) instanceof Player) {

            }
        }
    }

    private void callback(CommandSender sender, ArgumentSyntaxException e) {

    }
}
