package io.github.nitianstudio.event;

import io.github.nitianstudio.annotation.AutoRegistryEvent;
import io.github.nitianstudio.config.ServerProp;
import io.github.nitianstudio.server.NeoYouthCoreServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.ping.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerEvents {
    private static final Logger logger = LoggerFactory.getLogger(PlayerEvents.class);
    @AutoRegistryEvent(AsyncPlayerConfigurationEvent.class)
    public static void onJoin(AsyncPlayerConfigurationEvent event) {

        Player player = event.getPlayer();
        player.setPermissionLevel(4);
        event.setSpawningInstance(NeoYouthCoreServer.getInstanceContainer());

        player.setRespawnPoint(new Pos(0, 49, 0));
    }
}
