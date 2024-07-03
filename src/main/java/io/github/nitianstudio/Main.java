package io.github.nitianstudio;

import io.github.nitianstudio.server.NeoYouthCoreServer;

public class Main {


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) {
//        NeoYouthCoreServer instance = NeoYouthCoreServer.getInstance();

        NeoYouthCoreServer.instance();

//        container.setGenerator(unit -> unit.modifier().fillHeight(0, 48, Block.GRASS_BLOCK));
//        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

    }
}