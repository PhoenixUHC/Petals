# Petals

An opinionated framework for writing Minecraft gamemodes with scalability and developer experience in mind.

## How does it work

Each game runs in a dedicated Minecraft Bukkit server. This has several advantages:

- Allows your network to scale horizontally
- Isolates your games from one another, thus preventing two different plugins from interferring with another game's execution

To link all the servers together, Petals uses a proxy (currently only supports [Velocity](https://github.com/PaperMC/Velocity)).

Petals also requires a Redis cache to store game and players metadata.

## Groovy

Petals uses the Groovy programming language as an implementation for the Petals Bukkit and Velocity plugins.

### How do I learn Groovy?

If you know Java, you can pretty much start using Groovy right away. Groovy is well known for having a flat learning curve.

### Why Groovy?!

Petals uses Groovy for its amazing metaprogramming capabilities. We strongly recommend writing Petals plugins with Groovy to profit from some Petals features that will make your life easier, here's an example:

```groovy
// MyPlayer.groovy
interface MyPlayer extends io.github.petals.api.bukkit.structures.PetalsPlayer {
    String getSomeKey()
    void setSomeKey(String someKey)

    boolean getSpectator()
    void setSpectator(boolean spectator)
}

void doSomething(String uniqueId, String someKey) {
    PetalsPlugin.instance().player(uniqueId).ifPresent { p ->
        def myplayer = p as MyPlayer // Type coercion

        myplayer.someKey = someKey // The value of "someKey" will be stored in the Redis cache
        if (myplayer.spectator) // This fetches the value of "spectator" associated with the given player in the Redis cache, and will default to false if the value could not be found.
            println("$uniqueId is dead :(")
        else
            println("$uniqueId is alive! :)")
    }
}
```

Apart from type coercion, Groovy supports awesome features such as null safety, optional typing and optional static compilation, type inference, etc...

