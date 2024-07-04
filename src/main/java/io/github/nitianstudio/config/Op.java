package io.github.nitianstudio.config;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.nitianstudio.server.NycImpl.gson;

@Getter
@Setter
public class Op {
    @SerializedName("uuid")
    public String uuid;

    private static final Logger logger = LoggerFactory.getLogger(Op.class);

    private static final ConcurrentHashMap<String, Op> defaultInstance;// name -> op.settings
    public static final AtomicReference<ConcurrentHashMap<String, Op>> ops = new AtomicReference<>();
    public static final TypeToken<ConcurrentHashMap<String, Op>> type = new TypeToken<>() {};

    static {
        defaultInstance = new ConcurrentHashMap<>();
    }



    public static void run() {
        logger.info("Loading op.json");
        Paths.op.run(defaultInstance, ops, type);
    }
}
