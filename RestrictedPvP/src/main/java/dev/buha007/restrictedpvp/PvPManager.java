package dev.buha007.restrictedpvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import dev.buha007.restrictedpvp.handlers.BlockCommands;
import dev.buha007.restrictedpvp.handlers.Death;
import dev.buha007.restrictedpvp.handlers.PlayerDamage;
import dev.buha007.restrictedpvp.handlers.PlayerLogout;
import dev.buha007.restrictedpvp.handlers.Teleport;

public class PvPManager {

    private static Main main;
    private static HashMap<UUID, Long> playersInCombat = new HashMap<UUID, Long>();
    private static int combatTimeMillis;
    private static int checkInterval;
    private static BukkitTask checkTask;

    private PvPManager() {
    }

    public static void init(Main instance) {
        main = instance;
        combatTimeMillis = main.getConfig().getInt("combatTime") * 1000;
        checkInterval = main.getConfig().getInt("combatCheckInterval");
        checkTask = new PvPManager.CombatChecker().runTaskTimerAsynchronously(main, checkInterval, checkInterval);

        PluginManager pm = main.getServer().getPluginManager();
        pm.registerEvents(new PlayerDamage(), main);
        pm.registerEvents(new Teleport(main), main);
        pm.registerEvents(new PlayerLogout(main), main);
        pm.registerEvents(new Death(), main);
        pm.registerEvents(new BlockCommands(main), main);
    }

    public static void reload() {
        combatTimeMillis = main.getConfig().getInt("combatTime") * 1000;
        checkInterval = main.getConfig().getInt("combatCheckInterval");
        if (checkTask != null) {
            checkTask.cancel();
            checkTask = null;
        }
        checkTask = new CombatChecker().runTaskTimerAsynchronously(main, checkInterval, checkInterval);
    }

    public static boolean isInCombat(Player player) {
        return (playersInCombat.containsKey(player.getUniqueId()));
    }

    public static void putPlayersInCombat(Player victim, Player attacker) {
        UUID victimUUID = victim.getUniqueId();
        UUID attackerUUID = attacker.getUniqueId();

        if (victimUUID.compareTo(attackerUUID) == 0)
            return;

        if (!playersInCombat.containsKey(victimUUID)) {
            String message = Msg.COMBAT_TAGGED;
            message = message.replace("{attacker}", attacker.getName());
            if (!victim.hasPermission("restrictedpvp.bypass.fly")) {
                victim.setFlying(false);
                victim.setAllowFlight(false);
            }
            victim.sendMessage(message);
        }

        if (!playersInCombat.containsKey(attackerUUID)) {
            String message = Msg.COMBAT_TAG;
            message = message.replace("{victim}", victim.getName());
            if (!attacker.hasPermission("restrictedpvp.bypass.fly")) {
                attacker.setFlying(false);
                attacker.setAllowFlight(false);
            }
            attacker.sendMessage(message);
        }

        long currentTime = System.currentTimeMillis();
        playersInCombat.put(victimUUID, currentTime);
        playersInCombat.put(attackerUUID, currentTime);
    }

    public static void removePlayerInCombat(Player player) {
        playersInCombat.remove(player.getUniqueId());
        player.sendMessage(Msg.COMBAT_END);
    }

    public static class CombatChecker extends BukkitRunnable {
        /**
         * Check if player's combat has ended; If ended, add player's uuid to an array
         * list in order to remove it; Removal can't be done due to HashMap limitations
         * during iterating
         */

        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            ArrayList<UUID> uuidsToRemove = new ArrayList<UUID>();
            for (UUID uuid : PvPManager.playersInCombat.keySet()) {
                if (PvPManager.playersInCombat.get(uuid) + combatTimeMillis < currentTime) {
                    uuidsToRemove.add(uuid);
                }
            }

            for (UUID uuid : uuidsToRemove) {
                playersInCombat.remove(uuid);
                Player player = main.getServer().getPlayer(uuid);
                if (player != null)
                    player.sendMessage(Msg.COMBAT_END);
            }
        }

    }

}