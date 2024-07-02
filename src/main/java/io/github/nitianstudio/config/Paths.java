package io.github.nitianstudio.config;

import lombok.Getter;

import java.nio.file.Path;
import java.util.function.Supplier;

@Getter
public enum Paths implements Supplier<Path> {
    user_dir(System.getProperty("user.dir")),
    serverProp(user_dir, "server.properties.json"),
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
}
