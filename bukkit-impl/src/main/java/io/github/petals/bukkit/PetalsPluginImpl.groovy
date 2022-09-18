package io.github.petals.bukkit;

import io.github.petals.api.Petals;
import io.github.petals.PetalsImpl;
import io.github.petals.bukkit.api.PetalsPlugin;

import redis.clients.jedis.JedisPooled;

class PetalsPluginImpl implements PetalsPlugin {
    private JedisPooled pooled;

    Petals petals() {
        return new PetalsImpl(this.pooled);
    }

    def onEnable() {
        this.pooled = new JedisPooled("127.0.0.1", 6379);
    }
}

