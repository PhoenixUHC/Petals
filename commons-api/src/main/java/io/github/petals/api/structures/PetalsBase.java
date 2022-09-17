package io.github.petals.api.structures;

/**
 * Basic Petals object representation.
 */
public interface PetalsBase {
    /** @return the unique identifier for this Petals object. */
    String uniqueId();
    /** @return whether this Petals object exists on the database. */
    boolean exists();
    /** Deletes this Petals object from the database. */
    void delete();
}

