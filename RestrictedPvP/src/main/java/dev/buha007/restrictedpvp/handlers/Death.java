package dev.buha007.restrictedpvp.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import dev.buha007.restrictedpvp.PvPManager;

public class Death implements Listener {

    public Death() {
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (!PvPManager.isInCombat(player))
            return;
        PvPManager.removePlayerInCombat(player);
    }

}