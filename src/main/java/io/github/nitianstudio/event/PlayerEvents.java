package io.github.nitianstudio.event;

import io.github.nitianstudio.annotation.AutoRegistryEvent;
import io.github.nitianstudio.permission.Permissions;
import io.github.nitianstudio.config.Op;
import io.github.nitianstudio.config.ServerProp;
import io.github.nitianstudio.server.NeoYouthCoreServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;

import static io.github.nitianstudio.server.NycImpl.connManager;


public class PlayerEvents {

    @AutoRegistryEvent(AsyncPlayerConfigurationEvent.class)
    public static void onJoin(AsyncPlayerConfigurationEvent event) {

        Player player = event.getPlayer();
        if (connManager().getOnlinePlayers().size() + 1 > ServerProp.serverProps.get().maxPlayers) {
            player.kick("[Server] I'm going to overflow");
        }
        if (Op.has(player)) {
            player.setPermissionLevel(4);
            for (Permissions value : Permissions.values()) {
                player.addPermission(value.getPermission());
            }
        }

        event.setSpawningInstance(NeoYouthCoreServer.getInstanceContainer());

        player.setRespawnPoint(new Pos(0, 49, 0));
    }
}
