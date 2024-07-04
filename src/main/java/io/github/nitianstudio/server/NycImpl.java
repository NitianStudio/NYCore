package io.github.nitianstudio.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;

public class NycImpl {

    public static MinecraftServer server() {
        class Handler {
            private static final MinecraftServer server = MinecraftServer.init();
        }
        return Handler.server;
    }

    public static Gson gson() {
        class Handler {
            private static final Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        }
        return Handler.gson;
    }

    public static CommandManager commandManager() {
        return MinecraftServer.getCommandManager();
    }

    public static boolean isStopping() {
        return MinecraftServer.isStopping();
    }
}
