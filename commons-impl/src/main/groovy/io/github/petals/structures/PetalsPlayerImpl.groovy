package io.github.petals.structures;

class PetalsPlayerImpl extends PetalsBaseImpl {
    PetalsPlayerImpl(uniqueId, pooled) {
        super(uniqueId, pooled);
    }

    void delete() {
        def gameId = pooled.hget(uniqueId, "game");

        pooled.srem("$gameId:players", uniqueId);
        pooled.srem("players", uniqueId);
        super.delete();
    }
}

