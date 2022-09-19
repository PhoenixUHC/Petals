package io.github.petals.bukkit.structures;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class PetalsPlayerImpl extends io.github.petals.structures.PetalsPlayerImpl implements io.github.petals.api.bukkit.structures.PetalsPlayer {
    PetalsPlayerImpl(uniqueId, pooled) {
        super(uniqueId, pooled);
    }

    Optional<Player> player() {
        def player = Bukkit.getPlayer(UUID.fromString(this.uniqueId));
        return Optional.ofNullable(player);
    }
}

