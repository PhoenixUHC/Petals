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

    /**
     * Finds a value associated with the given key from the database.
     *
     * @param name the key.
     * @return the value.
     */
    Object getProperty(String name);
    /**
     * Assigns a value to the given key on the database.
     *
     * @param name the key.
     * @param object the value.
     */
    void setProperty(String name, Object object);
}

