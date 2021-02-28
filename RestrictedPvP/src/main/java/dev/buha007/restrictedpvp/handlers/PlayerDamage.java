package dev.buha007.restrictedpvp.handlers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import dev.buha007.restrictedpvp.PvPManager;

public class PlayerDamage implements Listener {

    public PlayerDamage() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.isCancelled() || e.getDamage() == 0)
            return;

        Entity victimEntity = e.getEntity();
        if (!(victimEntity instanceof Player))
            return;

        Entity damagerEntity = e.getDamager();
        Player damager;
        if (damagerEntity instanceof Player) {
            damager = (Player) damagerEntity;
        } else {
            if (damagerEntity instanceof Projectile) {
                if (((Projectile) damagerEntity).getShooter() instanceof Player) {
                    damager = (Player) ((Projectile) damagerEntity).getShooter();
                } else {
                    return;
                }
            } else {
                return;
            }
        }

        PvPManager.putPlayersInCombat((Player) victimEntity, damager);
    }
}