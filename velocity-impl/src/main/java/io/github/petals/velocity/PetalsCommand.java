package io.github.petals.velocity;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;

import io.github.petals.api.velocity.PetalsPlugin;
import io.github.petals.api.velocity.structures.PetalsGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class PetalsCommand {
    private final PetalsPlugin plugin;

    public PetalsCommand(PetalsPlugin plugin) {
        this.plugin = plugin;
    }

    private int statusNode(CommandContext<CommandSource> ctx) {
        Component message = Component.text("Petals", NamedTextColor.YELLOW);
        ctx.getSource().sendMessage(message);
        return 1;
    }

    private int createNode(CommandContext<CommandSource> ctx) {
        CommandSource src = ctx.getSource();
        if (!(src instanceof Player)) return 0;

        Player p = (Player) src;
        PetalsGame game = plugin.createGame(p.getCurrentServer().get().getServer());
        game.addPlayer(p.getUniqueId().toString()); // NOTE: Remove this

        return 1;
    }

    public BrigadierCommand createPetalsCommand() {
        return new BrigadierCommand(
            LiteralArgumentBuilder.<CommandSource>literal("petals")
                .then(
                    LiteralArgumentBuilder.<CommandSource>literal("create")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("game", string()).executes(this::createNode))
                )
                .executes(this::statusNode)
                .build()
        );
    }
}

