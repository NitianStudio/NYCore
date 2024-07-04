package io.github.nitianstudio.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.nitianstudio.server.NycImpl.gson;

@Getter
@Setter
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
        defaultInstance = new ServerProp();
        defaultInstance.setMaxPlayers(1);
        defaultInstance.setOnlineMode(true);
        defaultInstance.setIp("0.0.0.0");
        defaultInstance.setPort(25565);
    }

    private static final Logger logger = LoggerFactory.getLogger(ServerProp.class);


    public static final AtomicReference<ServerProp> serverProps = new AtomicReference<>();

    public static void run() {
        Paths serverProp = Paths.serverProp;
        logger.info("Loading server.properties.json");
        Paths.serverProp.run(defaultInstance, serverProps, ServerProp.class);
    }
}
