package io.github.nitianstudio.command;

import io.github.nitianstudio.server.NeoYouthCoreServer;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class StopCommand extends Command {


    public StopCommand() {
        super("stop");
        setDefaultExecutor((sender, ctx) -> {
            for (Player onlinePlayer : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                onlinePlayer.kick("[Server] stopped");
            }
            MinecraftServer.stopCleanly();
            System.exit(1);
        });
    }
}
