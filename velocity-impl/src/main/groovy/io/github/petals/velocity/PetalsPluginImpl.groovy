package io.github.petals.velocity

import com.velocitypowered.api.event.player.PlayerChooseInitialServerEvent;
import groovy.transform.CompileStatic
import io.github.petals.api.velocity.structures.PetalsGame
import io.github.petals.api.velocity.structures.PetalsPlayer
import io.github.petals.velocity.structures.PetalsPlayerImpl

import static groovy.transform.TypeCheckingMode.*;

import java.util.logging.Logger;

import com.google.common.io.*;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.messages.LegacyChannelIdentifier;

import io.github.petals.api.velocity.PetalsPlugin;
import io.github.petals.velocity.structures.PetalsGameImpl;
import redis.clients.jedis.JedisPooled;

@CompileStatic
@Plugin(id = "petals", name = "Petals", version = "0.1", url = "https://github.com/PhoenixUHC/Petals", authors = ["JanotLeLapin"])
class PetalsPluginImpl implements PetalsPlugin {
    private final JedisPooled pooled;
    private final ProxyServer proxy;
    private final Logger logger;

    @Inject
    PetalsPluginImpl(ProxyServer server, Logger logger) {
        this.pooled = new JedisPooled("127.0.0.1", 6379);
        this.proxy = server;
        this.logger = logger;
    }

    @Subscribe
    void onProxyInitialization(ProxyInitializeEvent e) {
        this.proxy.commandManager.register(new PetalsCommand(this).createPetalsCommand());
    }

    @Subscribe
    void onPlayerInitServer(PlayerChooseInitialServerEvent e) {
        def p = this.player(e.getPlayer().uniqueId.toString());
        if (!p.isPresent()) return;

        p.get().game().server().ifPresent(s -> e.setInitialServer(s));
    }

    Set<PetalsGameImpl> games() {
        new HashSet(pooled.smembers("games").collect { id -> new PetalsGameImpl(id, this) });
    }

    Optional<PetalsGameImpl> game(String uniqueId) {
        PetalsGameImpl g = new PetalsGameImpl(uniqueId, this);
        return g.exists() ? Optional.of(g) : Optional.empty();
    }

    @CompileStatic(SKIP)
    PetalsGameImpl createGame(RegisteredServer server) {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();

        PetalsGameImpl game = new PetalsGameImpl(uniqueId, this);
        game.start = -1;
        game.server = server.serverInfo.name;

        pooled.sadd("games", uniqueId);

        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeByte(0);
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
        server.sendPluginMessage(new LegacyChannelIdentifier("petals:channel"), buffer.toByteArray());

        new PetalsGameImpl(uniqueId, this);
    }

    Optional<PetalsPlayerImpl> player(String uniqueId) {
        PetalsPlayerImpl p = new PetalsPlayerImpl(uniqueId, this);
        return p.exists() ? Optional.of(p) : Optional.empty();
    }

    JedisPooled pooled() {
        this.pooled;
    }

    ProxyServer proxy() {
        this.proxy;
    }

    Logger logger() {
        this.logger;
    }
}

