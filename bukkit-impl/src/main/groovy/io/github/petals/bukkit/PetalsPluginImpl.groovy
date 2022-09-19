package io.github.petals.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.petals.api.bukkit.PetalsPlugin;
import io.github.petals.bukkit.structures.PetalsGameImpl;

import redis.clients.jedis.JedisPooled;

class PetalsPluginImpl extends JavaPlugin implements PetalsPlugin {
    private JedisPooled pooled;

    void onEnable() {
        this.pooled = new JedisPooled("127.0.0.1", 6379);
    }

    void onDisable() {
        this.games().each { it.delete() };
    }

    Set<PetalsGameImpl> games() {
        return pooled.smembers("games")
            .collect { new PetalsGameImpl(it, pooled) };
    }

    PetalsGameImpl createGame() {
        def uniqueId = PetalsGameImpl.create(pooled).uniqueId();
        return new PetalsGameImpl(uniqueId, pooled);
    }
}

