package amata1219.walkure.spigot.listener;

import amata1219.walkure.spigot.Constants;
import amata1219.walkure.spigot.Walkure;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import static org.bukkit.ChatColor.*;

public class PlayerEatFishListener implements Listener {

    private final Walkure plugin = Walkure.instance();

    @EventHandler
    public void on(PlayerItemConsumeEvent event) {
        if (!Constants.SERVER_SELECTOR.isSimilar(event.getItem())) return;

        Player player = event.getPlayer();

        String prefix = AQUA + "??? > " + GRAY;
        player.sendMessage(prefix + "ホウ……");

        runTaskLater(25, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "これを見つけるとは貴様、なかなかやるではないか");
        });

        runTaskLater(25 + 80, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "そうだな、褒美にこれでもくれてやろう");
        });

        runTaskLater(25 + 80 + 65, () -> {
            playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.getInventory().addItem(Constants.INFINITY_SNOWBALL);
        });

        runTaskLater(25 + 80 + 65 + 20, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "冬を存分に楽しむがいい");
        });

        runTaskLater(25 + 80 + 65 + 20 + 40, () -> player.sendMessage(prefix + "ではな"));

    }

    private void runTaskLater(long delay, Runnable action) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, action, delay);
    }

    private void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1.0f, 2.0f);
    }

}
