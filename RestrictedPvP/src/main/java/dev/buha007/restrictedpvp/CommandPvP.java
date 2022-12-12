package dev.buha007.restrictedpvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class CommandPvP implements CommandExecutor {

    private Main main;

    public CommandPvP(Main instance) {
        this.main = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("pvp"))
            return true;

        if (!sender.hasPermission("restrictedpvp.pvpcommand")) {
            sender.sendMessage(Msg.NO_PERMISSION);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            main.reloadConfig();
            Msg.reload();
            PvPManager.reload();
            Msg.loadMessages();
            sender.sendMessage(Msg.CONFIG_RELOADED);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("listmessages")) {
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', "\n&8&l<< &cRestricted&4PvP &bmessages &8&l>>"));
            sender.sendMessage(" ");
            sender.sendMessage(Msg.COMBAT_TAGGED);
            sender.sendMessage(Msg.COMBAT_TAG);
            sender.sendMessage(Msg.COMBAT_END);
            sender.sendMessage(Msg.BLOCK_COMMAND);
            sender.sendMessage(Msg.TELEPORT_DENY);
            sender.sendMessage(Msg.LOGOUT_COMBAT_MESSAGE);
            sender.sendMessage(Msg.CONFIG_RELOADED);
            sender.sendMessage(Msg.NO_PERMISSION);
            sender.sendMessage(" ");
            return true;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "\n&8&l<< &cRestricted&4PvP &eHelp &8&l>>"));
        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8/&cpvp &4reload &8(&7reload config&8)"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&8/&cpvp &4listMessages &8(&7list all messages from messages.yml&8)"));
        sender.sendMessage(" ");
        sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&8&l&o>> &7&oRestrictedPvP developed by Bufnita"));

        return true;
    }

}