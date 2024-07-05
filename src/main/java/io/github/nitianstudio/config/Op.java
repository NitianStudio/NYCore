package io.github.nitianstudio.config;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.minestom.server.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.nitianstudio.server.NycImpl.gson;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"uuid"})
@Slf4j
public class Op {
    @SerializedName("uuid")
    public String uuid;
    public static final AtomicReference<ConcurrentHashMap<String, Op>> ops = new AtomicReference<>();
    public static final TypeToken<ConcurrentHashMap<String, Op>> type = new TypeToken<>() {};


    public static void put(String key, UUID uuid) {
        ops.get().put(key, new Op(uuid.toString()));
    }

    public static void remove(String key) { ops.get().remove(key); }

    public static boolean has(Player player) {
        return ops.get().containsKey(player.getUsername()) && ops.get().get(player.getUsername()).uuid.equals(player.getUuid().toString());
    }


    public static void run() {
        log.info("Loading op.json");
        Paths.op.run(() -> new ConcurrentHashMap<>(), ops, type);
    }

    public static void save() {
        Paths.op.save(ops.get());
    }
}
