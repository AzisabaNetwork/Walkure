package amata1219.walkure.spigot.listener;

import amata1219.walkure.spigot.Constants;
import amata1219.walkure.spigot.Walkure;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.GRAY;

public class InfinitySnowballListener implements Listener {

    private final Walkure plugin = Walkure.instance();

    private Method hasTotem;

    public InfinitySnowballListener() {
        try {
            hasTotem = EntityLiving.class.getDeclaredMethod("e", DamageSource.class);
            hasTotem.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

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
            player.getInventory().addItem(Constants.ETERNAL_FORCE_BLIZZARD);
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

        if (!Constants.ETERNAL_FORCE_BLIZZARD.isSimilar(event.getItem())) return;

        event.setCancelled(true);

        Player player = event.getPlayer();

        World world = ((CraftWorld) player.getWorld()).getHandle();
        EntityHuman entityHuman = ((CraftPlayer) player).getHandle();
        EntitySnowball entitySnowball = new EntitySnowball(world, entityHuman);
        entitySnowball.a(entityHuman, entityHuman.pitch, entityHuman.yaw, 0.0F, 1.5F, 1.0F);
        world.addEntity(entitySnowball);

        player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 0.5F, 0.4F / (Constants.RANDOM.nextFloat() * 0.4F + 0.8F));
    }

    private final FixedMetadataValue metadata = new FixedMetadataValue(plugin, this);

    @EventHandler
    public void on(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;

        Snowball snowball = (Snowball) event.getEntity();
        if (!(snowball.getShooter() instanceof Player)) return;

        Player shooter = (Player) snowball.getShooter();
        if (shooter.getInventory().getItemInMainHand().isSimilar(Constants.ETERNAL_FORCE_BLIZZARD))
            snowball.setMetadata(Constants.ETERNAL_FORCE_BLIZZARD_METADATA_NAME, metadata);
    }

    @EventHandler
    public void on(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Snowball)) return;

        Snowball snowball = (Snowball) event.getEntity();
        if (!(snowball.hasMetadata(Constants.ETERNAL_FORCE_BLIZZARD_METADATA_NAME))) return;

        Location loc = snowball.getLocation();
        loc.setDirection(snowball.getVelocity());

        loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 16, Constants.ICE_MATERIAL_DATA);
        loc.getWorld().playSound(loc, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f + Constants.RANDOM.nextFloat());

        if (!(event.getHitEntity() instanceof Player)) return;

        Player hit = (Player) event.getHitEntity();
        if (!hit.getInventory().getItemInMainHand().isSimilar(Constants.ETERNAL_FORCE_BLIZZARD)) return;

        final double damage = Constants.ETERNAL_FORCE_BLIZZARD_DAMAGE;

        EntityPlayer player = ((CraftPlayer) hit).getHandle();
        EntitySnowball entitySnowball = ((CraftSnowball) snowball).getHandle();
        DamageSource damageSource = DamageSource.projectile(entitySnowball, entitySnowball.getShooter());

        double before = hit.getHealth();
        if (before <= damage) {
            try {
                if (!((boolean) hasTotem.invoke(player, damageSource))) {
                    hit.setMetadata(Constants.DEATH_FLAG_METADATA_NAME, new FixedMetadataValue(Walkure.instance(), snowball));
                    player.killEntity();
                    return;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        hit.setHealth(before - damage);
        player.getCombatTracker().trackDamage(damageSource, (float) before, (float) damage);
        player.setAbsorptionHearts(player.getAbsorptionHearts() - (float) damage);
        player.world.broadcastEntityEffect(player, (byte) 2);
        hit.setNoDamageTicks(0);
    }

    @EventHandler(ignoreCancelled = true)
    public void on(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof  Player && event.getDamager() instanceof Snowball)) return;

        Snowball snowball = (Snowball) event.getDamager();
        if (!(snowball.hasMetadata(Constants.ETERNAL_FORCE_BLIZZARD_METADATA_NAME))) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void on(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<MetadataValue> metadataList = player.getMetadata(Constants.DEATH_FLAG_METADATA_NAME);
        if(metadataList == null || metadataList.isEmpty()) return;

        FixedMetadataValue metadata = (FixedMetadataValue) metadataList.get(0);
        player.removeMetadata(Constants.DEATH_FLAG_METADATA_NAME, Walkure.instance());

        Player shooter = (Player) ((Snowball) metadata.value()).getShooter();
        String message = player.getName() + " は " + shooter.getName() + " ";
        switch (Constants.RANDOM.nextInt(4)) {
            case 0:
            case 1: {
                message += "の エターナルフォースブリザード で凍死した";
                break;
            }
            case 2: {
                message += "により周囲の大気ごと凍結されて死んだ";
                break;
            }
            default: {
                message += "が生み出した永遠の猛吹雪で凍え死んだ";
            }
        }

        event.setDeathMessage(message);
    }

}
