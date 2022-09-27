package io.github.petals.api.bukkit;

import io.github.petals.api.bukkit.structures.PetalsGame;
import org.bukkit.plugin.Plugin;

public interface Petal extends Plugin {
    default void onCreateGame(PetalsGame game) {};
    default void onStartGame(PetalsGame game) {};
}
