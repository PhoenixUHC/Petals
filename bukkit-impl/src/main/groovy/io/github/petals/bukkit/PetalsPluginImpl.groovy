package io.github.petals.bukkit;

import groovy.transform.CompileStatic
import io.github.petals.api.bukkit.Petal
import io.github.petals.bukkit.structures.PetalsPlayerImpl
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.*;

import io.github.petals.api.bukkit.PetalsPlugin;
import io.github.petals.bukkit.structures.PetalsGameImpl
import redis.clients.jedis.JedisPooled

import java.util.function.Consumer;

@CompileStatic
class PetalsPluginImpl extends JavaPlugin implements PetalsPlugin, PluginMessageListener {
    private JedisPooled pooled;
    private String gameId;

    private static void callback(Consumer<Petal> callback) {
        Petal petal = (Petal) Bukkit.pluginManager.plugins.find { it instanceof Petal };
        if (petal == null) return;

        try {
            callback.accept(petal);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    void onEnable() {
        def host = System.getenv("REDIS_HOST") ?: "127.0.0.1"
        def port = System.getenv("REDIS_PORT") as int ?: 6379
        this.pooled = new JedisPooled(host, port);
        this.server.messenger.registerIncomingPluginChannel(this, "petals:channel", this);
    }

    void onDisable() {
        PetalsPlugin.game().ifPresent(g -> g.delete());
    }

    void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel != "petals:channel") return;

        ByteArrayDataInput buffer = ByteStreams.newDataInput(bytes);
        switch (buffer.readByte()) {
            case 0:
                this.gameId = new UUID(buffer.readLong(), buffer.readLong()).toString();
                callback { it.onCreateGame(PetalsPlugin.game().get()) }
                break;
            case 1:
                PetalsPlugin.game().ifPresent(g -> {
                    g.setProperty("start", String.valueOf(System.currentTimeMillis()));
                    callback { it.onStartGame(g) }
                });
                break;
        }
    }

    Set<PetalsGameImpl> games() {
        new HashSet(
            pooled.smembers("games").collect { new PetalsGameImpl(it, pooled) }
        );
    }

    Optional<String> gameId() {
        Optional.ofNullable(this.gameId);
    }

    Optional<PetalsGameImpl> game(String uniqueId) {
        PetalsGameImpl g = new PetalsGameImpl(uniqueId, pooled);
        return (Optional<PetalsGameImpl>) (g.exists() ? Optional.of(g) : Optional.empty())
    }

    @Override
    Optional<PetalsPlayerImpl> player(String uniqueId) {
        PetalsPlayerImpl p = new PetalsPlayerImpl(uniqueId, pooled);
        return (Optional<PetalsPlayerImpl>) (p.exists() ? Optional.of(p) : Optional.empty())
    }
}

