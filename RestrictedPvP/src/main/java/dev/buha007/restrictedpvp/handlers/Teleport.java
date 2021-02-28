package dev.buha007.restrictedpvp.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import dev.buha007.restrictedpvp.PvPManager;
import dev.buha007.restrictedpvp.Main;
import dev.buha007.restrictedpvp.Msg;

public class Teleport implements Listener {

    private Main main;

    public Teleport(Main instance) {
        main = instance;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        if (!PvPManager.isInCombat(player))
            return;
        if (player.hasPermission("restrictedpvp.bypass.teleport"))
            return;

        if (main.getConfig().getBoolean("teleportBlock.all")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Msg.TELEPORT_DENY);
            return;
        }

        if (e.getCause().equals(TeleportCause.CHORUS_FRUIT)) {
            if (main.getConfig().getBoolean("teleportBlock.chorusFruit")) {
                e.setCancelled(true);
                player.sendMessage(Msg.TELEPORT_DENY);
                return;
            }
        }

        if (e.getCause().equals(TeleportCause.ENDER_PEARL)) {
            if (main.getConfig().getBoolean("teleportBlock.enderPearl")) {
                e.setCancelled(true);
                player.sendMessage(Msg.TELEPORT_DENY);
                return;
            }
        }

    }

}