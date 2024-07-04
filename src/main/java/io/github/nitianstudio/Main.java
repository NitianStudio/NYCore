package io.github.nitianstudio;

import io.github.nitianstudio.server.NeoYouthCoreServer;
import io.github.nitianstudio.server.NycImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.nitianstudio.server.NycImpl.commandManager;

@Slf4j
public class Main {

//    public static Console

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static volatile Terminal terminal;
    public static void main(String[] args) {
        NeoYouthCoreServer.instance();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        executor.submit(() -> {
            try {
                terminal = TerminalBuilder.terminal();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer((lineReader, parsedLine, list) -> {

                    })
                    .build();
            while (true) {
                String command;
                try {
                    command = reader.readLine();
                    commandManager().execute(commandManager().getConsoleSender(), command);
                } catch (UserInterruptException e) {
                    // Handle Ctrl + C
                    System.exit(0);
                    return;
                } catch (EndOfFileException e) {
                    return;
                }
            }
        });


//        NeoYouthCoreServer instance = NeoYouthCoreServer.getInstance();



//        container.setGenerator(unit -> unit.modifier().fillHeight(0, 48, Block.GRASS_BLOCK));
//        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

    }
}