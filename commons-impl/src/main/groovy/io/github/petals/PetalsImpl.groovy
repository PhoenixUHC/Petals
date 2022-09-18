package io.github.petals;

import io.github.petals.api.Petals;
import io.github.petals.structures.*;

import redis.clients.jedis.JedisPooled;

class PetalsImpl implements Petals {
    JedisPooled pooled;

    PetalsImpl(pooled) {
        this.pooled = pooled;
    }

    public Set<PetalsGameImpl> games() {
        return this.pooled
            .stream()
            .map { new PetalsGameImpl(it, this.pooled) }
            .collect(Collectors.toSet());
    }

    public <T extends PetalsGameImpl> Set<T> games(Class<T> clazz) {
        return new HashSet<>();
    }

    public <T extends PetalsGameImpl> T createGame(Class<T> clazz) {
        PetalsGameImpl g = new PetalsGameImpl(this.pooled);
        return g;
    }
}

