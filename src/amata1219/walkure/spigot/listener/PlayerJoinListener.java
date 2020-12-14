package amata1219.walkure.spigot.listener;

import amata1219.walkure.spigot.Constants;
import amata1219.walkure.spigot.extension.BukkitRunner;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.ChatColor.*;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        tryGivingDefaultItemsTo(player);

        BukkitRunner.of(0, () -> {
            player.sendMessage(new String[]{
                    GOLD + "【TIP】",
                    GOLD + "アイテム欄にあります" + RED + "\"魚\"" + GOLD + "を手に持ち右クリックしますと、",
                    GOLD + "サーバー選択画面を開くことができます！  ごーとぅーあじさば！＞＜ｗ"
            });
            playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.8f);
        }).append(40, () -> {
            player.sendMessage(new String[]{
                    "",
                    AQUA + "【期間限定】",
                    AQUA + "冬だ！  雪だ！  雪合戦だ(ﾟ∀ﾟ)！！！",
                    AQUA + "という訳で、ロビー鯖で" + WHITE + "「雪合戦」" + AQUA + "が出来るようになりました！",
                    AQUA + "厨二ちっくな輝きの雪玉は宣戦の証！  奴らを狙いまくれ！＞＜ｗ"
            });
            playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.8f);
        }).runTaskLater();
    }

    private void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    @EventHandler
    public void on(PlayerRespawnEvent event) {
        tryGivingDefaultItemsTo(event.getPlayer());
    }

    private void tryGivingDefaultItemsTo(Player player) {
        tryGivingItemTo(player, Constants.SERVER_SELECTOR);
        tryGivingItemTo(player, Constants.ETERNAL_FORCE_BLIZZARD);
    }

    private void tryGivingItemTo(Player player, ItemStack item) {
        Inventory inventory = player.getInventory();
        if (!inventory.contains(item)) inventory.addItem(item);
    }

}
