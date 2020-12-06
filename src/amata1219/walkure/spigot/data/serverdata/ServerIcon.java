package amata1219.walkure.spigot.data.serverdata;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ServerIcon {

    public final Material type;
    public final short damage;

    public ServerIcon(Material type, short damage) {
        this.type = type;
        this.damage = damage;
    }

    public ItemStack buildIconBase() {
        return new ItemStack(type);
    }

}
