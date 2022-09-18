package io.github.petals.structures;

import io.github.petals.api.structures.*;

class PetalsBaseImpl implements PetalsBase {
    private String uniqueId;

    PetalsBaseImpl(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    String uniqueId() {
        return this.uniqueId;
    }

    boolean exists() {
        return true;
    }

    void delete() {

    }
}

