package io.github.petals.bukkit.structures;

import groovy.transform.CompileStatic
import io.github.petals.api.bukkit.structures.PetalsGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import redis.clients.jedis.JedisPooled;

@CompileStatic
class PetalsPlayerImpl extends io.github.petals.structures.PetalsPlayerImpl implements io.github.petals.api.bukkit.structures.PetalsPlayer {
    PetalsPlayerImpl(String uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    Optional<Player> player() {
        def player = Bukkit.getPlayer(UUID.fromString(this.uniqueId()));
        return Optional.ofNullable(player);
    }

    @Override
    PetalsGame game() {
        def gid = this.pooled.hget(this.uniqueId(), "game");
        return new PetalsGameImpl(gid, this.pooled);
    }
}

