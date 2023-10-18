package org.hakerplugin.guishop.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hakerplugin.guishop.Shop.ShopManager;

@CommandAlias("shop")
public class ShopCommands extends BaseCommand {

    @Default
    public void openShopGui(CommandSender sender){
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Ты не проведёшь меня, жалкая машина!");
            return;
        }
        ShopManager.getInstance().loadItemsFromConfig(); // Загружаем items из конфига
        ShopManager.getInstance().openShopGui(player); // Открываем магазин
    }
}
