package org.hakerplugin.guishop.Listener;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.ScrollingGui;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.hakerplugin.guishop.GUIShop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CommandAlias("shop")
public class Commands extends BaseCommand {
    List<String> myList = new ArrayList<String>(Arrays.asList(GUIShop.getInstance().getConfig().getString("items").split(", ")));
    Gui hopp = Gui.gui()
            .title(Component.text("APPLY"))
            .type(GuiType.HOPPER)
            .create();

    @Default
    public void invent(CommandSender sender){
        myList.replaceAll(s -> s.replaceAll("\\[|\\{|\\]|\\}", ""));
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Ты не проведёшь меня, жалкая машина!");
            return;
        }
        ScrollingGui gui = Gui.scrolling()
                .title(Component.text("SHOP"))
                .rows(6)
                .pageSize(45)
                .scrollType(ScrollType.VERTICAL)
                .create();
        gui.disableAllInteractions();
        gui.setItem(6, 1,ItemBuilder.from(Material.BIRCH_BUTTON).name(Component.text("Down")).asGuiItem(Event -> gui.next()));
        gui.setItem(6, 9,ItemBuilder.from(Material.DARK_OAK_BUTTON).name(Component.text("Up")).asGuiItem(Event -> gui.previous()));
        gui.setItem(6, 5,ItemBuilder.from(Material.BARRIER).name(Component.text("EXIT")).asGuiItem(Event -> gui.close(player)));
        for(int i=0; i< myList.size();i++){
            if(i % 3 == 0){
                GuiItem item = ItemBuilder.from(Material.valueOf(myList.get(i).substring(9))).name(Component.text(myList.get(i).substring(9) + " - $" + myList.get(i+1).substring(6))).asGuiItem(event -> {
                    event.setCancelled(true);
                    this.hoppAdd(event, player, gui);
                    hopp.open(player);
                    });
                    gui.addItem(item);
            }
        }
        gui.open(player);
    }
    public void hoppAdd(InventoryClickEvent rootEvent, Player player, ScrollingGui gui){
        int price = Integer.valueOf(myList.get(rootEvent.getSlot()*3+1).substring(6));
        int count = Integer.valueOf(myList.get(rootEvent.getSlot()*3+2).substring(6));
        //hopp.updateTitle("APPLY\n - " + String.valueOf(rootEvent.getCurrentItem()) + " " + String.valueOf(price));
        GuiItem hoppItem = ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE).name(Component.text("APPLY")).asGuiItem(event -> {
            event.setCancelled(true);
            if (!(player.getInventory().contains(Material.DIAMOND,price))){
                return;
            }
            player.getInventory().addItem(rootEvent.getCurrentItem());
            player.getInventory().removeItem(new ItemStack(Material.DIAMOND, price));
        });
        hopp.setItem(1,1, hoppItem);
        hopp.setItem(1,2, hoppItem);
        hoppItem = ItemBuilder.from(rootEvent.getCurrentItem()).amount(count).asGuiItem(event -> event.setCancelled(true));
        hopp.setItem(1,3, hoppItem);
        hoppItem = ItemBuilder.from(Material.RED_STAINED_GLASS_PANE).name(Component.text("DISCARD")).asGuiItem(event -> {
            event.setCancelled(true);
            hopp.close(player);
            gui.open(player);

        });
        hopp.setItem(1,4, hoppItem);
        hopp.setItem(1,5, hoppItem);
        //return event;
    }
}
