package io.github.nitianstudio.command;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.CommandSender;

public class ServerCommands {
    public static void run(CommandManager manager) {
        manager.setUnknownCommandCallback(ServerCommands::fallback);
        manager.register(new StopCommand());
    }

    private static void fallback(CommandSender sender, String command) {

    }
}
