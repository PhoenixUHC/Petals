package io.github.petals.bukkit;

import groovy.transform.CompileStatic;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.ByteBuffer;

import io.github.petals.api.bukkit.PetalsPlugin;
import io.github.petals.bukkit.structures.PetalsGameImpl;

import redis.clients.jedis.JedisPooled;

@CompileStatic
class PetalsPluginImpl extends JavaPlugin implements PetalsPlugin, PluginMessageListener {
    private JedisPooled pooled;
    private String gameId;

    void onEnable() {
        this.pooled = new JedisPooled("127.0.0.1", 6379);
        this.server.messenger.registerIncomingPluginChannel(this, "petals:channel", this);
    }

    void onDisable() {
        this.games().each { it.delete() };
    }

    void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("petals:channel")) return;

        switch (bytes[0]) {
            case 0:
                byte[] uuid = Arrays.copyOfRange(bytes, 1, 17);
                ByteBuffer buffer = ByteBuffer.wrap(uuid);
                this.gameId = new UUID(buffer.getLong(), buffer.getLong()).toString();
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
        return g.exists() ? Optional.of(g) : Optional.empty();
    }
}

