package amata1219.walkure.spigot.listener;

import amata1219.walkure.spigot.Constants;
import amata1219.walkure.spigot.Walkure;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntitySnowball;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

import static org.bukkit.ChatColor.*;

public class InfinitySnowballListener implements Listener {

    private final Walkure plugin = Walkure.instance();

    @EventHandler
    public void on(PlayerItemConsumeEvent event) {
        if (!Constants.SERVER_SELECTOR.isSimilar(event.getItem())) return;

        Player player = event.getPlayer();

        String prefix = AQUA + "???" + GRAY + " > ";
        runTaskLater(25, () -> {
            player.sendMessage(prefix + "ホウ……");
            playSound(player, Sound.BLOCK_GLASS_BREAK);
        });

        runTaskLater(25 + 50, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "これを見つけるとは貴様、なかなかやるではないか");
        });

        runTaskLater(25 + 50 + 70, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "そうだな、褒美にこれでもくれてやろう");
        });

        runTaskLater(25 + 50 + 70 + 65, () -> {
            playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.getInventory().addItem(Constants.INFINITY_SNOWBALL);
        });

        runTaskLater(25 + 50 + 70 + 65 + 40, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "冬を存分に楽しむがいい");
        });

        runTaskLater(25 + 50 + 70 + 65 + 40 + 40, () -> {
            playSound(player, Sound.BLOCK_GLASS_BREAK);
            player.sendMessage(prefix + "ではな");
        });
    }

    private void runTaskLater(long delay, Runnable action) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, action, delay);
    }

    private void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1.0f, 2.0f);
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) return;;

        if (!Constants.INFINITY_SNOWBALL.isSimilar(event.getItem())) return;

        event.setCancelled(true);

        Player player = event.getPlayer();

        World world = ((CraftWorld) player.getWorld()).getHandle();
        EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
        EntitySnowball entitySnowball = new EntitySnowball(world, entityHuman);
        entitySnowball.a(entityHuman, entityHuman.pitch, entityHuman.yaw, 0.0F, 1.5F, 1.0F);
        world.addEntity(entitySnowball);

        player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f + Constants.RANDOM.nextFloat());
    }

}
