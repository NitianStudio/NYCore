package io.github.nitianstudio.command;

import lombok.Getter;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;

public class Arguments {

    public static final ArgumentEntity player = ArgumentType.Entity("player").onlyPlayers(true);
}
