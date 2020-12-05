package amata1219.walkure.spigot;

import amata1219.niflheimr.enchantment.GleamEnchantment;
import amata1219.niflheimr.listener.InventoryOperationListener;
import amata1219.walkure.Channels;
import amata1219.walkure.spigot.config.ServerConfiguration;
import amata1219.walkure.spigot.config.Yaml;
import amata1219.walkure.spigot.data.processor.ServerInformationSynthesizer;
import amata1219.walkure.spigot.listener.PlayerJoinListener;
import amata1219.walkure.spigot.listener.ResponseReceiveListener;
import amata1219.walkure.spigot.registry.RequesterRegistry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Walkure extends JavaPlugin {

    private static Walkure plugin;

    public final Yaml serversConfig = new Yaml("servers.yml");
    public final ServerConfiguration serverConfiguration = new ServerConfiguration(serversConfig);
    public final ServerInformationSynthesizer serverInformationSynthesizer = new ServerInformationSynthesizer(serverConfiguration);

    @Override
    public void onEnable() {
        plugin = this;

        serversConfig.saveDefault();
        serverConfiguration.load();

        RequesterRegistry requesterRegistry = new RequesterRegistry();

        getServer().getMessenger().registerIncomingPluginChannel(this, Channels.BUNGEE_CORD, new ResponseReceiveListener(requesterRegistry));
        getServer().getMessenger().registerOutgoingPluginChannel(this, Channels.BUNGEE_CORD);

        registerGleamEnchantment();

        registerEventListeners(
                new InventoryOperationListener(),
                new PlayerJoinListener()
        );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public static Walkure instance() {
        return instance();
    }

    private void registerGleamEnchantment() {
        Field acceptingNew;
        try {
            acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            Enchantment.registerEnchantment(GleamEnchantment.INSTANCE);
            acceptingNew.set(null, false);
            acceptingNew.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void registerEventListeners(Listener... listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

}
