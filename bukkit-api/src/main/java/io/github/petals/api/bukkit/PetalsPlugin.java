package io.github.petals.api.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import io.github.petals.api.Petals;

/**
 * The Petals Bukkit plugin.
 */
public interface PetalsPlugin extends Plugin {
    /** @return the instance of the plugin. */
    public static PetalsPlugin instance() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Petals");
        return plugin == null ? null : (PetalsPlugin) plugin;
    }

    /** @return an instance of the Petals API object. */
    Petals petals();
}

