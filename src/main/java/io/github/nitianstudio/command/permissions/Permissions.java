package io.github.nitianstudio.command.permissions;


import lombok.Getter;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.Nullable;

@Getter
public enum Permissions {
    op("all.op");

    private final Permission permission;

    Permissions(String s) {
        permission = new Permission(s);
    }
    Permissions(String s, @Nullable CompoundBinaryTag data) {
        permission = new Permission(s, data);
    }



}
