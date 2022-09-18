package io.github.petals.structures;

import io.github.petals.api.structures.*;

class PetalsGameImpl extends PetalsBaseImpl implements PetalsGame {
    PetalsGameImpl(uniqueId, pooled) {
        super(uniqueId, pooled);
    }

    boolean running() {
        return time() > -1;
    }

    void delete() {
        this.pooled.srem("games", this.uniqueId);
        super.delete();
    }
}

