package io.github.nitianstudio.server;

import io.github.nitianstudio.command.ServerCommands;
import io.github.nitianstudio.config.Op;
import io.github.nitianstudio.config.ServerProp;
import io.github.nitianstudio.event.ServerEvents;
import io.github.nitianstudio.terminal.NycTerminal;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

import java.util.concurrent.atomic.AtomicReference;

import static io.github.nitianstudio.server.NycImpl.server;

@Slf4j
public class NeoYouthCoreServer {

    private static final AtomicReference<NeoYouthCoreServer> instance = new AtomicReference<>();
    @Getter
    private static InstanceContainer instanceContainer;

    public static boolean closed = false;

    public NeoYouthCoreServer() {
        MinecraftServer server = server();
        ServerProp.run();
        Op.run();
        preInit();
        ServerCommands.run(MinecraftServer.getCommandManager());
        ServerEvents.run(MinecraftServer.getGlobalEventHandler());
        log.info("loading terminal");

        log.info("Starting server on{}:{}", prop().ip, prop().port);

        server.start(prop().ip, prop().port);
        NycTerminal.run();

        MinecraftServer.getSchedulerManager().buildShutdownTask(this::onShutdown);

        onStart();

    }



    private void onStart() {
        InstanceManager manager = MinecraftServer.getInstanceManager();
        instanceContainer = manager.createInstanceContainer();

    }

    private void onShutdown() {
        log.info("Server shutting down...");
    }

    public static ServerProp prop() {
        return ServerProp.serverProps.get();
    }

    public void preInit() {
        if (prop().onlineMode) {
            if (!MojangAuth.isEnabled()) {
                MojangAuth.init();
            }

        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static NeoYouthCoreServer instance() {
        if (instance.get() == null) {
            instance.set(new NeoYouthCoreServer());
        }
        return instance.get();
    }

    public static void close() {
        instance.set(null);
    }
}
