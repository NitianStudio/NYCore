package io.github.nitianstudio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.nitianstudio.config.Paths;
import io.github.nitianstudio.config.ServerProp;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.server.ServerListPingEvent;
import net.minestom.server.event.server.ServerTickMonitorEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.ping.ResponseData;
import net.minestom.server.utils.mojang.MojangUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
    public static final AtomicReference<ServerProp> serverProps = new AtomicReference<>();
    public static void main(String[] args) {
        Paths serverProp = Paths.serverProp;
        try {
            Files.createDirectories(serverProp.getParent().get());
        } catch (IOException ignored) {}
        if (!Files.exists(serverProp.get())) {
            try (BufferedWriter bw = Files.newBufferedWriter(serverProp.get(), StandardCharsets.UTF_8)) {
                gson.toJson(ServerProp.defaultInstance, bw);
            } catch (IOException ignored) {}
        }
        try(BufferedReader br = Files.newBufferedReader(serverProp.get(), StandardCharsets.UTF_8)) {
            serverProps.set(gson.fromJson(br, ServerProp.class));
        } catch (IOException ignored) {}
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceManager manager = MinecraftServer.getInstanceManager();

        InstanceContainer container = manager.createInstanceContainer();

        container.setGenerator(unit -> unit.modifier().fillHeight(0, 48, Block.GRASS_BLOCK));
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(container);

            player.setRespawnPoint(new Pos(0, 49, 0));
        });
        handler.addListener(ServerListPingEvent.class, event -> {
            ServerProp serverProps = Main.serverProps.get();
            ResponseData responseData = event.getResponseData();
            responseData.setMaxPlayer(serverProps.maxPlayers);

//            event.getResponseData().setMaxPlayer(serverProp.getMaxPlayers());
        });
        handler.addListener(ServerTickMonitorEvent.class, event -> {
            if (((int) event.getTickMonitor().getTickTime()) % 1000 == 0) {
                // auto load
                try (BufferedReader bw = Files.newBufferedReader(serverProp.get(), StandardCharsets.UTF_8)) {
                    serverProps.set(gson.fromJson(bw, ServerProp.class));
                } catch (IOException ignored) {}
            }
        });
        ServerProp serverProps = Main.serverProps.get();
        minecraftServer.start(serverProps.ip, serverProps.port);

    }
}