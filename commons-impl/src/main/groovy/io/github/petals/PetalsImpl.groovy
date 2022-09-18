package io.github.petals;

import io.github.petals.api.*;
import io.github.petals.api.structures.*;

class PetalsImpl implements Petals {
    public Set<PetalsGame> games() {
        return new HashSet<>();
    }

    public <T extends PetalsGame> Set<T> games(Class<T> clazz) {
        return new HashSet<>();
    }
}

