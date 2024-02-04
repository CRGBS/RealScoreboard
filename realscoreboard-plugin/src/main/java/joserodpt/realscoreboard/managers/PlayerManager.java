package joserodpt.realscoreboard.managers;

/*
 *   ____            _ ____                     _                         _
 *  |  _ \ ___  __ _| / ___|  ___ ___  _ __ ___| |__   ___   __ _ _ __ __| |
 *  | |_) / _ \/ _` | \___ \ / __/ _ \| '__/ _ \ '_ \ / _ \ / _` | '__/ _` |
 *  |  _ <  __/ (_| | |___) | (_| (_) | | |  __/ |_) | (_) | (_| | | | (_| |
 *  |_| \_\___|\__,_|_|____/ \___\___/|_|  \___|_.__/ \___/ \__,_|_|  \__,_|
 *
 *
 * Licensed under the MIT License
 * @author José Rodrigues
 * @link https://github.com/joserodpt/RealScoreboard
 */

import joserodpt.realscoreboard.api.RealScoreboardAPI;
import joserodpt.realscoreboard.api.config.RSBConfig;
import joserodpt.realscoreboard.api.managers.AbstractPlayerManager;
import joserodpt.realscoreboard.api.scoreboard.RPlayerHook;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager implements AbstractPlayerManager {

    private final Map<UUID, RPlayerHook> playerHooks = new HashMap<>();
    private final RealScoreboardAPI rsa;

    public PlayerManager(RealScoreboardAPI rsa) {
        this.rsa = rsa;
    }

    @Override
    public boolean isVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    @Override
    public RPlayerHook getPlayerHook(UUID uuid) {
        return this.getPlayerHooks().get(uuid);
    }

    @Override
    public Map<UUID, RPlayerHook> getPlayerHooks() {
        return this.playerHooks;
    }

    public void checkPlayer(Player p) {
        rsa.getPlayerManager().getPlayerHooks().put(p.getUniqueId(),
                new RPlayerHook(p, !RSBConfig.file().getList("Config.Bypass-Worlds").contains(p.getWorld().getName()) && rsa.getDatabaseManager().getPlayerData(p.getUniqueId()).isScoreboardON()));
    }
}