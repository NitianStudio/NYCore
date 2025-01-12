package io.github.nitianstudio.command;

import io.github.nitianstudio.config.Op;
import io.github.nitianstudio.permission.Permissions;
import lombok.val;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.ConsoleSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import static io.github.nitianstudio.command.Arguments.player;
import static io.github.nitianstudio.permission.Permissions.op;
import static io.github.nitianstudio.server.NycImpl.commandManager;

public class OpCommand extends Command {


    public OpCommand() {
       this("op");

        addSyntax(this::executor, player);
    }
    public OpCommand(@NotNull String name) {
        super(name);
        player.setCallback(this::callback);

    }

    private void executor(CommandSender sender, CommandContext ctx) {
        if (sender instanceof Player user && !user.hasPermission(op.getPermission())) {
            return;
        }
        val players = ctx.get(player).find(commandManager().getConsoleSender());
        for (var entity : players) {
            if (entity instanceof Player player) {
                for (Permissions value : Permissions.values()) {
                    player.addPermission(value.getPermission());
                }
                Op.put(player.getUsername(), player.getUuid());
                sender.sendMessage("set-op "+player.getUsername()+" success");
            }
        }
        Op.save();

    }

    private void callback(CommandSender sender, ArgumentSyntaxException e) {

    }
}
