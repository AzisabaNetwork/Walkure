package amata1219.walkure.spigot.itemstack;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullCreator {

    public static ItemStack createFrom(String base64){
        int hash = base64.hashCode();
        UUID uuid = new UUID(hash, hash);
        String data = "{SkullOwner:{Id:\"" + uuid + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}";
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        return Bukkit.getUnsafe().modifyItemStack(item, data);
    }

}
