package org.hakerplugin.guishop;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.hakerplugin.guishop.Commands.ShopCommands;

import java.util.logging.Logger;

public final class GUIShop extends JavaPlugin {

    private static GUIShop instance;
    @Override
    public void onEnable() {
        Logger log = getLogger();
        log.info("Ihr Plugin läuft, willkommen!");
        instance = this;
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(new ShopCommands());
    }
    public static GUIShop getInstance(){
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
