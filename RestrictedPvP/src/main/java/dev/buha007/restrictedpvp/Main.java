package dev.buha007.restrictedpvp;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

    private ConfigAccessor configuration;

    public ConsoleCommandSender console;

    public boolean economyEnabled = false;

    @Override
    public void onEnable() {
        configuration = new ConfigAccessor(this, "config.yml");

        console = getServer().getConsoleSender();
        console.sendMessage(
                ChatColor.translateAlternateColorCodes('&', "&cRestrictedPvP for 1.19.2 &edeveloped by Bufnita"));

        PvPManager.init(this);
        Msg.init(this);

        this.getCommand("pvp").setExecutor(new CommandPvP(this));
    }

    // <configuration>
    @Override
    public FileConfiguration getConfig() {
        return configuration.getConfig();
    }

    @Override
    public void reloadConfig() {
        configuration.reloadConfig();
    }
    // </configuration>

    @Override
    public void onDisable() {

    }

}
