package amata1219.walkure.spigot;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.Random;

import static org.bukkit.ChatColor.*;

public class Constants {

    public static final Random RANDOM = new Random();

    public static final ItemStack SERVER_SELECTOR = new ItemStack(Material.RAW_FISH);
    public static final ItemStack ETERNAL_FORCE_BLIZZARD = new ItemStack(Material.SNOW_BALL);

    public static final String ETERNAL_FORCE_BLIZZARD_METADATA_NAME = "walkure:eternal-force-blizzard";
    public static final String DEATH_FLAG_METADATA_NAME = "walkure:death-flag";

    public static final MaterialData ICE_MATERIAL_DATA = new MaterialData(Material.ICE);

    public static final double ETERNAL_FORCE_BLIZZARD_DAMAGE = 1.0;

    static {
        ItemMeta serverSelectorMeta = SERVER_SELECTOR.getItemMeta();
        serverSelectorMeta.setDisplayName("" + RESET + AQUA + "サーバーに遊びに行く！");
        serverSelectorMeta.setLore(Arrays.asList(
                GRAY + "右クリックでサーバー選択画面を開きます！",
                GRAY + "食用ではありません！"
        ));
        SERVER_SELECTOR.setItemMeta(serverSelectorMeta);

        ItemMeta infinitySnowballMeta = ETERNAL_FORCE_BLIZZARD.getItemMeta();
        infinitySnowballMeta.setDisplayName("" + RESET + AQUA + "エターナルフォースブリザード");
        infinitySnowballMeta.setLore(Arrays.asList(
                WHITE + "一瞬で相手の周囲の大気ごと氷結させる",
                WHITE + "相手は死ぬ"
        ));
        infinitySnowballMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        infinitySnowballMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ETERNAL_FORCE_BLIZZARD.setItemMeta(infinitySnowballMeta);
    }

}
