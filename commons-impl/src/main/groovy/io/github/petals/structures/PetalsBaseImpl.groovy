package io.github.petals.structures;

import io.github.petals.api.structures.PetalsBase;

class PetalsBaseImpl implements PetalsBase {
    private String uniqueId

    PetalsBaseImpl(uniqueId) {
        this.uniqueId = uniqueId
    }

    String uniqueId() {
        return this.uniqueId
    }

    boolean exists() {
        return true
    }

    void delete() {

    }
}

