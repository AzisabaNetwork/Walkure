package amata1219.walkure.spigot.ui;

import amata1219.niflheimr.dsl.InventoryUI;
import amata1219.niflheimr.dsl.component.InventoryFormat;
import amata1219.niflheimr.dsl.component.InventoryLayout;
import amata1219.niflheimr.dsl.component.InventoryLines;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.config.ServerConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ServerSelectorUI implements InventoryUI {

    private final ServerConfiguration config = Walkure.instance().serverConfiguration;
    private final HashMap<String, Integer> serversToPlayerCounts;

    public ServerSelectorUI(HashMap<String, Integer> serversToPlayerCounts) {
        this.serversToPlayerCounts = serversToPlayerCounts;
    }

    @Override
    public InventoryLayout layout(Player viewer) {
        return build(InventoryLines.x6, l -> {

        });
    }

}
