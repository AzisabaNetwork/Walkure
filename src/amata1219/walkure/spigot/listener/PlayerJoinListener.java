package amata1219.walkure.spigot.listener;

import amata1219.walkure.spigot.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Inventory inventory = event.getPlayer().getInventory();
        if (!inventory.contains(Constants.SERVER_SELECTOR))
            inventory.addItem(Constants.SERVER_SELECTOR);
    }

}
