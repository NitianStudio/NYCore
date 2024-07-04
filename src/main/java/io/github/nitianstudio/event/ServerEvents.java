package io.github.nitianstudio.event;

import io.github.nitianstudio.annotation.AutoRegistryEvent;
import io.github.nitianstudio.config.ServerProp;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.ping.ResponseData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static io.github.nitianstudio.config.Paths.serverProp;
import static io.github.nitianstudio.server.NycImpl.gson;

public class ServerEvents {

    private static final Logger logger = LoggerFactory.getLogger(ServerEvents.class);

    public static void run(GlobalEventHandler handler) {
//        handler.addListener(AsyncPlayerConfigurationEvent.class, PlayerEvents::onJoin);
        registry(handler, PlayerEvents.class);
        registry(handler, ServerEvents.class);
    }

    public static <T> void registry(GlobalEventHandler handler,Class<T> clazz) {
        for (Method method : clazz.getMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(AutoRegistryEvent.class)) {
                AutoRegistryEvent annotation = method.getAnnotation(AutoRegistryEvent.class);
                handler.addListener(annotation.value(), event -> {
                    try {
                        method.invoke(null, event);
                    } catch (Exception e) {
                        logger.error(method.getName() + "isn't load");
                    }
                });
            }
        }
    }
    @AutoRegistryEvent(ServerListPingEvent.class)
    public static void ping(ServerListPingEvent event) {
        ServerProp serverProps = ServerProp.serverProps.get();
        ResponseData responseData = event.getResponseData();
        responseData.setMaxPlayer(serverProps.maxPlayers);
    }

    @AutoRegistryEvent(ServerTickMonitorEvent.class)
    public static void tick(ServerTickMonitorEvent event) {

        if (((int) event.getTickMonitor().getTickTime()) % 1000 == 0) {
            // auto load
            try (BufferedReader bw = Files.newBufferedReader(serverProp.get(), StandardCharsets.UTF_8)) {
                ServerProp.serverProps.set(gson().fromJson(bw, ServerProp.class));
            } catch (IOException ignored) {}
        }
    }
}
