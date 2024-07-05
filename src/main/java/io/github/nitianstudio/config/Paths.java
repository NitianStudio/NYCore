package io.github.nitianstudio.config;

import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static io.github.nitianstudio.server.NycImpl.gson;

@SuppressWarnings("LoggingSimilarMessage")
@Getter
@Slf4j
public enum Paths implements Supplier<Path> {
    user_dir(System.getProperty("user.dir")),
    serverProp(user_dir, "server.properties.json"),
    op(user_dir, "op.json"),
    config(user_dir, "config"),
    plugin(user_dir, "plugin"),
    ;

    private final Paths parent;

    private final Path path;


    Paths(String path) {
        this.parent = null;
        this.path = Path.of(path);
    }

    Paths(Paths paths, String subPath) {
        this.parent = paths;
        path = paths.path.resolve(subPath);
    }

    @Override
    public Path get() {
        return path;
    }

    public void checkParent() {
        if (parent != null)
            try {
                Files.createDirectories(parent.get());
            } catch (IOException ignored) {}
    }

    public <T> void save(T instance) {
        try(BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            gson().toJson(instance, bw);
        } catch (IOException e) {
            log.error("I don't save " + path.toFile().getName());
        }
    }

    public <T> void load(AtomicReference<T> reference, Class<T> clazz) {
        try(BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reference.set(gson().fromJson(br, clazz));
        } catch (IOException e) {
            log.error("I don't load {}", path.toFile().getName());
        }
    }

    public <T> void load(AtomicReference<T> reference, TypeToken<T> token) {
        try(BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            reference.set(gson().fromJson(br, token.getType()));
        } catch (IOException e) {
            log.error("I don't load {}", path.toFile().getName());
        }
    }

    public <T> void run(T defaultInstance, AtomicReference<T> reference, Class<T> clazz) {
        checkParent();
        if (!Files.exists(path)) {
            save(defaultInstance);
        }
        load(reference, clazz);
    }

    public <T> void run(T defaultInstance, AtomicReference<T> reference, TypeToken<T> token) {
        checkParent();

        if (!Files.exists(path)) {
            save(defaultInstance);
        }
        load(reference, token);
    }
}
