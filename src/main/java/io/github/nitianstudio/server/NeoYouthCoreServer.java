package io.github.nitianstudio.server;

import io.github.nitianstudio.command.ServerCommands;
import io.github.nitianstudio.config.Op;
import io.github.nitianstudio.config.ServerProp;
import io.github.nitianstudio.event.ServerEvents;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

import static io.github.nitianstudio.server.NycImpl.server;

public class NeoYouthCoreServer {

    private static final Logger logger = LoggerFactory.getLogger(NeoYouthCoreServer.class);


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
        logger.info("loading terminal");

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
