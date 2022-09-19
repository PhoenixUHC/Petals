package io.github.petals.structures;

import io.github.petals.structures.PetalsGameImpl

import io.github.petals.api.structures.*;

class PetalsGameImpl extends PetalsBaseImpl implements PetalsGame {
    static Set<PetalsGameImpl> games(pooled) {
        return pooled
            .smembers("games")
            .collect { new PetalsGameImpl(it, pooled) }
    }

    static PetalsGameImpl create(pooled) {
        def uniqueId = UUID.randomUUID().toString();

        def game = new PetalsGameImpl(uniqueId, pooled);
        game.start = -1

        pooled.sadd("games", uniqueId);

        return game;
    }

    PetalsGameImpl(uniqueId, pooled) {
        super(uniqueId, pooled);
    }

    boolean running() {
        return time() > -1;
    }

    void delete() {
        players().each { it.delete() } // Remove players
        pooled.srem("games", this.uniqueId);

        super.delete();
    }

    Set<PetalsPlayerImpl> players() {
        return pooled.smembers("$uniqueId:players")
            .collect { new PetalsPlayerImpl(pooled, uniqueId) };
    }

    PetalsPlayerImpl addPlayer(String uniqueId) {
        pooled.sadd("${this.uniqueId}:players", uniqueId);
        pooled.sadd("players", uniqueId);

        pooled.hset(uniqueId, "game", this.uniqueId);

        return new PetalsPlayerImpl(uniqueId, pooled);
    }
}

