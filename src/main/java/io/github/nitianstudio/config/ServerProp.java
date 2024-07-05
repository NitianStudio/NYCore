package io.github.nitianstudio.config;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ServerProp {
    @SerializedName("max-players")
    public int maxPlayers;

    @SerializedName("online-mode")
    public boolean onlineMode;

    @SerializedName("server-ip")
    public String ip;

    @SerializedName("server-port")
    public int port;

    public static final ServerProp defaultInstance;



    static {
        defaultInstance = new ServerProp(1, true, "0.0.0.0", 25565);
    }

    public static final AtomicReference<ServerProp> serverProps = new AtomicReference<>();

    public static void run() {
        Paths serverProp = Paths.serverProp;
        log.info("Loading server.properties.json");
        Paths.serverProp.run(defaultInstance, serverProps, ServerProp.class);
    }
}
