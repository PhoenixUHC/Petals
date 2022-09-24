package io.github.petals.api.bukkit;

import java.util.Optional;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import io.github.petals.api.bukkit.structures.PetalsGame;

/**
 * The Petals Bukkit plugin.
 */
public interface PetalsPlugin extends Plugin {
    /** @return the instance of the plugin. */
    public static PetalsPlugin instance() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Petals");
        return plugin == null ? null : (PetalsPlugin) plugin;
    }

    /** @return the current game for this Bukkit server. */
    public static Optional<PetalsGame> game() {
        final PetalsPlugin plugin = PetalsPlugin.instance();
        return plugin.game(plugin.gameId().orElse(null));
    }

    /** @return each game currently stored on the database. */
    Set<PetalsGame> games();

    /** @return the game associated with the given uniqueId. */
    Optional<PetalsGame> game(String uniqueId);

    /** @return the current game identifier */
    Optional<String> gameId();
}

