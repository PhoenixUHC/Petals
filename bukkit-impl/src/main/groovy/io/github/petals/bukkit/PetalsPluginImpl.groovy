package io.github.petals.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.petals.api.Petals;
import io.github.petals.api.bukkit.PetalsPlugin;
import io.github.petals.PetalsImpl;

import redis.clients.jedis.JedisPooled;

class PetalsPluginImpl extends JavaPlugin implements PetalsPlugin {
    private JedisPooled pooled;

    Petals petals() {
        return new PetalsImpl(this.pooled);
    }

    void onEnable() {
        this.pooled = new JedisPooled("127.0.0.1", 6379);
    }

    void onDisable() {
        this.petals().games().each { it.delete() };
    }
}

