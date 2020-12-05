package amata1219.walkure.spigot.listener;

import amata1219.walkure.spigot.Constants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerOpenServerSelectorListener implements Listener {

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) return;

        if (Constants.SERVER_SELECTOR.isSimilar(event.getItem())) return;


    }

}
