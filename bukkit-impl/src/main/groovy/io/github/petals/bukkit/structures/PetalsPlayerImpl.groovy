package io.github.petals.bukkit.structures;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import redis.clients.jedis.JedisPooled;

class PetalsPlayerImpl extends io.github.petals.structures.PetalsPlayerImpl implements io.github.petals.api.bukkit.structures.PetalsPlayer {
    PetalsPlayerImpl(String uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    Optional<Player> player() {
        def player = Bukkit.getPlayer(UUID.fromString(this.uniqueId));
        return Optional.ofNullable(player);
    }
}

