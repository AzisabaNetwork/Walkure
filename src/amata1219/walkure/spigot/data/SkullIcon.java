package amata1219.walkure.spigot.data;

import amata1219.walkure.spigot.itemstack.SkullCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SkullIcon extends ServerIcon {

    private final String base64;

    public SkullIcon(String base64) {
        super(Material.SKULL_ITEM, (short) 3);
        this.base64 = base64;
    }

    @Override
    public ItemStack buildIconBase() {
        return SkullCreator.createFrom(base64);
    }
}
