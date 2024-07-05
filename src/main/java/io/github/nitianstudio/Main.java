package io.github.nitianstudio;

import io.github.nitianstudio.server.NeoYouthCoreServer;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.nitianstudio.server.NycImpl.commandManager;

public class Main {

//    public static Console



    public static void main(String[] args) {
        NeoYouthCoreServer.instance();



//        NeoYouthCoreServer instance = NeoYouthCoreServer.getInstance();



//        container.setGenerator(unit -> unit.modifier().fillHeight(0, 48, Block.GRASS_BLOCK));
//        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

    }
}