package io.github.nitianstudio.command;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

import static io.github.nitianstudio.server.NycImpl.connManager;

public class StopCommand extends Command {


    public StopCommand() {
        super("stop");
        setDefaultExecutor((sender, ctx) -> {
            for (Player onlinePlayer : connManager().getOnlinePlayers()) {
                onlinePlayer.kick("[Server] stopped");
            }
            MinecraftServer.stopCleanly();

            System.exit(1);
        });
    }
}
