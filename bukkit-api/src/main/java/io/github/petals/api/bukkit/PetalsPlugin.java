package io.github.petals.api.bukkit;

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

    /** @return each game currently stored on the database. */
    Set<PetalsGame> games();
    /**
     * Creates a Petals game and returns it.
     *
     * @return the new game.
     */
    PetalsGame createGame();
}

