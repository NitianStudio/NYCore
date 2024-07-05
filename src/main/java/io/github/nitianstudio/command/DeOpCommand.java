package io.github.nitianstudio.command;

import io.github.nitianstudio.config.Op;
import io.github.nitianstudio.permission.Permissions;
import lombok.val;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;


import static io.github.nitianstudio.command.Arguments.player;
import static io.github.nitianstudio.permission.Permissions.op;
import static io.github.nitianstudio.server.NycImpl.commandManager;

public class DeOpCommand extends OpCommand {
    public DeOpCommand() {
        super("de-op");
        addSyntax(this::deOp, player);
    }

    private void deOp(CommandSender sender, CommandContext ctx) {
        if (sender instanceof Player user && !user.hasPermission(op.getPermission())) {
            return;
        }
        val players = ctx.get(player).find(commandManager().getConsoleSender());
        for (var entity : players) {
            if (entity instanceof Player player) {
                for (Permissions value : Permissions.values()) {
                    player.removePermission(value.getPermission());
                }
                Op.remove(player.getUsername());
                sender.sendMessage("del-op "+player.getUsername()+" success");
            }
        }
        Op.save();
    }


}
