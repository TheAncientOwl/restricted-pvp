package dev.buha007.restrictedpvp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Msg {
    private static ConfigAccessor messages;

    public static String COMBAT_TAGGED, COMBAT_TAG, COMBAT_END, BLOCK_COMMAND, TELEPORT_DENY, LOGOUT_COMBAT_MESSAGE,
            CONFIG_RELOADED, NO_PERMISSION;

    public static void init(Main instance) {
        messages = new ConfigAccessor(instance, "messages.yml");
        Msg.loadMessages();
    }

    public static void reload() {
        messages.reloadConfig();
        Msg.loadMessages();
    }

    public static void loadMessages() {
        FileConfiguration cfg = messages.getConfig();

        COMBAT_TAGGED = ChatColor.translateAlternateColorCodes('&', cfg.getString("combatTagged"));
        COMBAT_TAG = ChatColor.translateAlternateColorCodes('&', cfg.getString("combatTag"));
        COMBAT_END = ChatColor.translateAlternateColorCodes('&', cfg.getString("combatEnd"));
        BLOCK_COMMAND = ChatColor.translateAlternateColorCodes('&', cfg.getString("blockCommand"));
        TELEPORT_DENY = ChatColor.translateAlternateColorCodes('&', cfg.getString("teleportDeny"));
        LOGOUT_COMBAT_MESSAGE = ChatColor.translateAlternateColorCodes('&', cfg.getString("logoutCombatMessage"));
        CONFIG_RELOADED = ChatColor.translateAlternateColorCodes('&', cfg.getString("configReloaded"));
        NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', cfg.getString("noPermission"));
    }

}