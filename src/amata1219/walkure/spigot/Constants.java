package amata1219.walkure.spigot;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Random;

import static org.bukkit.ChatColor.*;

public class Constants {

    public static final Random RANDOM = new Random();
    public static final ItemStack SERVER_SELECTOR = new ItemStack(Material.RAW_FISH);

    static {
        ItemMeta meta = SERVER_SELECTOR.getItemMeta();
        meta.setDisplayName(AQUA + "遊ぶサーバーを選ぶ");
        meta.setLore(Arrays.asList(
                GRAY + "右クリックでサーバー選択画面を開きます！"
        ));
        SERVER_SELECTOR.setItemMeta(meta);
    }

}
