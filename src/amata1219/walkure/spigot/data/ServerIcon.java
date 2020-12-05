package amata1219.walkure.spigot.data;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ServerIcon {

    private final Material type;
    private final short damage;

    public ServerIcon(Material type, short damage) {
        this.type = type;
        this.damage = damage;
    }

    public ItemStack buildIconBase() {
        ItemStack item = new ItemStack(type);
        item.setDurability(damage);
        return item;
    }

}
