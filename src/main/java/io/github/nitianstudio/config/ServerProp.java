package io.github.nitianstudio.config;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

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
}
