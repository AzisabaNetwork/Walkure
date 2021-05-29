package amata1219.niflheimr.dsl;

import amata1219.niflheimr.dsl.component.InventoryFormat;
import amata1219.niflheimr.dsl.component.InventoryLayout;
import amata1219.niflheimr.dsl.component.InventoryLines;
import amata1219.walkure.spigot.Walkure;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.function.Consumer;

public interface InventoryUI {

    InventoryLayout layout(Player viewer);

    default InventoryLayout build(InventoryFormat format, Consumer<InventoryLayout> settings) {
        InventoryLayout layout = new InventoryLayout(format);
        settings.accept(layout);
        return layout;
    }

    default InventoryLayout build(InventoryLines lines, Consumer<InventoryLayout> settings) {
        return build(lines.format, settings);
    }

    default InventoryLayout build(InventoryType type, Consumer<InventoryLayout> settings) {
        return build(new InventoryFormat(type), settings);
    }

    default void openInventory(Player player) {
        player.openInventory(layout(player).buildInventory());
    }

    default void openInventoryAsynchronously(Player player) {
        Walkure plugin = Walkure.instance();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskAsynchronously(plugin, () -> {
            Inventory inventory = layout(player).buildInventory();
            scheduler.runTask(plugin, () -> player.openInventory(inventory));
        });
    }

}
