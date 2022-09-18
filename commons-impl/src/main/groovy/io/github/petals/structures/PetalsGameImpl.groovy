package io.github.petals.structures;

import io.github.petals.api.structures.*;

class PetalsGameImpl implements PetalsGame {
    PetalsGameImpl(String uniqueId) {
        super(uniqueId);
    }

    long time() {
        return -1;
    }

    boolean running() {
        return time() > -1;
    }

    def invokeMethod(String methodName, Object args) {
        println "called $methodName"
    }
}

