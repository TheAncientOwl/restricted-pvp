package dev.buha007.restrictedpvp.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dev.buha007.restrictedpvp.PvPManager;
import dev.buha007.restrictedpvp.Main;
import dev.buha007.restrictedpvp.Msg;

public class BlockCommands implements Listener {

    private Main main;

    public BlockCommands(Main instance) {
        main = instance;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String message = e.getMessage();
        Player player = e.getPlayer();
        if (!PvPManager.isInCombat(player))
            return;
        if (player.hasPermission("restrictedpvp.allowcommandsincombat"))
            return;
        for (String allowedCommand : main.getConfig().getStringList("whitelistedCommands")) {
            if (message.startsWith(allowedCommand)) {
                return;
            }
        }
        e.setCancelled(true);
        player.sendMessage(Msg.BLOCK_COMMAND);
    }

}