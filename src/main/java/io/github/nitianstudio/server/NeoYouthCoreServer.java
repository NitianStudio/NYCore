package io.github.nitianstudio.server;

import io.github.nitianstudio.command.ServerCommands;
import io.github.nitianstudio.config.ServerProp;
import io.github.nitianstudio.event.ServerEvents;
import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NeoYouthCoreServer {

    private static final Logger logger = LoggerFactory.getLogger(NeoYouthCoreServer.class);
    @Getter
    private static InstanceContainer instanceContainer;

    public NeoYouthCoreServer() {
        MinecraftServer server = MinecraftServer.init();
        ServerProp.run();
        preInit();
        ServerCommands.run(MinecraftServer.getCommandManager());
        ServerEvents.run(MinecraftServer.getGlobalEventHandler());
        logger.info("Starting server on" + prop().ip + ":" + prop().port);
        server.start(prop().ip, prop().port);

        MinecraftServer.getSchedulerManager().buildShutdownTask(this::onShutdown);
        onStart();
    }

    private void onStart() {
        InstanceManager manager = MinecraftServer.getInstanceManager();
        instanceContainer = manager.createInstanceContainer();
    }

    private void onShutdown() {
        logger.info("Server shutting down...");
    }

    public ServerProp prop() {
        return ServerProp.serverProps.get();
    }

    public void preInit() {
        if (prop().onlineMode) {
            MojangAuth.init();
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public static NeoYouthCoreServer instance() {
        final class Handler {
            private static final NeoYouthCoreServer instance = new NeoYouthCoreServer();
        }
        return Handler.instance;
    }
}
