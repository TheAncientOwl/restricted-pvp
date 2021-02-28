package dev.buha007.restrictedpvp.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.buha007.restrictedpvp.PvPManager;
import dev.buha007.restrictedpvp.Main;
import dev.buha007.restrictedpvp.Msg;

public class PlayerLogout implements Listener {

    private Main main;

    public PlayerLogout(Main instance) {
        main = instance;
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (!PvPManager.isInCombat(player))
            return;
        if (player.hasPermission("restrictedpvp.bypass.killonlogout"))
            return;

        if (main.getConfig().getBoolean("whenLogout.kill")) {
            PvPManager.removePlayerInCombat(player);
            player.setHealth(0);
            if (main.getConfig().getBoolean("whenLogout.broadcast")) {
                String message = Msg.LOGOUT_COMBAT_MESSAGE;
                message = message.replace("{player}", player.getName());
                main.getServer().broadcastMessage(message);
            }
        }
    }

}