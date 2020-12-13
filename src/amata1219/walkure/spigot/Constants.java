package amata1219.walkure.spigot;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Random;

import static org.bukkit.ChatColor.*;

public class Constants {

    public static final Random RANDOM = new Random();

    public static final ItemStack SERVER_SELECTOR = new ItemStack(Material.RAW_FISH);
    public static final ItemStack INFINITY_SNOWBALL = new ItemStack(Material.SNOW_BALL);

    static {
        ItemMeta serverSelectorMeta = SERVER_SELECTOR.getItemMeta();
        serverSelectorMeta.setDisplayName(AQUA + "サーバーに遊びに行く！");
        serverSelectorMeta.setLore(Arrays.asList(
                GRAY + "右クリックでサーバー選択画面を開きます！",
                GRAY + "食用ではありません！"
        ));
        SERVER_SELECTOR.setItemMeta(serverSelectorMeta);

        ItemMeta infinitySnowballMeta = INFINITY_SNOWBALL.getItemMeta();
        infinitySnowballMeta.setDisplayName(WHITE + "雪礫");
        infinitySnowballMeta.setLore(Arrays.asList(
                GRAY + "北風、冬ノ将軍ヨリ賜ツタ褒美ノ品",
                GRAY + "此ノ礫ニ限リ無シ"
        ));
        infinitySnowballMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        infinitySnowballMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        INFINITY_SNOWBALL.setItemMeta(infinitySnowballMeta);
    }

}
