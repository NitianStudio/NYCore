package io.github.nitianstudio.command;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.CommandSender;
import net.minestom.server.scoreboard.Team;

public class ServerCommands {
    public static void run(CommandManager manager) {
        manager.setUnknownCommandCallback(ServerCommands::fallback);
        manager.register(new StopCommand());
        manager.register(new OpCommand());
        manager.register(new DeOpCommand());
    }

    private static void fallback(CommandSender sender, String command) {

    }
}
