package io.github.nitianstudio.terminal;

import lombok.extern.slf4j.Slf4j;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.suggestion.Suggestion;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.listener.TabCompleteListener;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.github.nitianstudio.server.NycImpl.commandManager;

@Slf4j
public class NycTerminal {


    private static volatile Terminal terminal;



    public static void run() {
        try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                try {
                    terminal = TerminalBuilder.terminal();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                LineReader reader = LineReaderBuilder.builder()
                        .terminal(terminal)
                        .completer(NycTerminal::complete)
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
        }

    }

    private static void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
        if (line.wordIndex() == 0) {
            final String commandString = line.word().toLowerCase();
            candidates.addAll(commandManager().getDispatcher().getCommands().stream()
                    .map(Command::getName).filter(name -> commandString.isBlank() || name.toLowerCase().startsWith(commandString))
                    .map(Candidate::new)
                    .toList()
            );
        } else {
            final String text = line.line();
            final Suggestion suggestion = TabCompleteListener.getSuggestion(commandManager().getConsoleSender(), text);
            if (suggestion != null) {
                suggestion.getEntries().stream()
                        .map(SuggestionEntry::getEntry)
                        .map(Candidate::new)
                        .forEach(candidates::add);
            }
        }
    }
}
