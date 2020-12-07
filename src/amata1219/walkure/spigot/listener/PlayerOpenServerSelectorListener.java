package amata1219.walkure.spigot.listener;

import amata1219.walkure.Channels;
import amata1219.walkure.spigot.Constants;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.registry.RequesterRegistry;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerOpenServerSelectorListener implements Listener {

    private final RequesterRegistry registry;

    public PlayerOpenServerSelectorListener(RequesterRegistry registry) {
        this.registry = registry;
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Action action = event.getAction();
        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();
        if (player.isSneaking()) return;

        if (!Constants.SERVER_SELECTOR.isSimilar(event.getItem())) return;

        //応急処置
        Constants.TEMPORARY_UI.openInventoryAsynchronously(player);
        if (true) return;

        long id = System.nanoTime();

        registry.register(id, player);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(Channels.REQUEST);
        out.writeLong(id);

        float pitch = 1.0f + Constants.RANDOM.nextFloat();
        player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, pitch);

        player.sendPluginMessage(Walkure.instance(), Channels.BUNGEE_CORD, out.toByteArray());

        event.setCancelled(true);
    }

}
