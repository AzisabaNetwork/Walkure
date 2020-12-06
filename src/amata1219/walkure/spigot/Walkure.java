package amata1219.walkure.spigot;

import amata1219.niflheimr.enchantment.GleamEnchantment;
import amata1219.niflheimr.listener.InventoryOperationListener;
import amata1219.walkure.Channels;
import amata1219.walkure.spigot.config.ServerConfiguration;
import amata1219.walkure.spigot.config.Yaml;
import amata1219.walkure.spigot.data.processor.ServerInformationSynthesizer;
import amata1219.walkure.spigot.listener.PlayerJoinListener;
import amata1219.walkure.spigot.listener.PlayerOpenServerSelectorListener;
import amata1219.walkure.spigot.listener.ResponseReceiveListener;
import amata1219.walkure.spigot.registry.RequesterRegistry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class Walkure extends JavaPlugin {

    private static Walkure instance;

    private Yaml config;
    private ServerConfiguration serverConfiguration;
    private ServerInformationSynthesizer serverInformationSynthesizer;

    @Override
    public void onEnable() {
        instance = this;

        config = new Yaml("servers.yml");
        config.saveDefault();

        serverConfiguration = new ServerConfiguration(config);
        serverConfiguration.load();

        serverInformationSynthesizer = new ServerInformationSynthesizer(serverConfiguration);


        RequesterRegistry requesterRegistry = new RequesterRegistry();

        getServer().getMessenger().registerIncomingPluginChannel(this, Channels.BUNGEE_CORD, new ResponseReceiveListener(requesterRegistry));
        getServer().getMessenger().registerOutgoingPluginChannel(this, Channels.BUNGEE_CORD);

        registerGleamEnchantment();

        registerEventListeners(
                new InventoryOperationListener(),
                new PlayerJoinListener(),
                new PlayerOpenServerSelectorListener(requesterRegistry)
        );
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public static Walkure instance() {
        return instance;
    }

    public Yaml config() {
        return config;
    }

    public ServerConfiguration serverConfiguration() {
        return serverConfiguration;
    }

    public ServerInformationSynthesizer serverInformationSynthesizer() {
        return serverInformationSynthesizer;
    }

    private void registerGleamEnchantment() {
        Field acceptingNew = null;
        try {
            acceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            acceptingNew.setAccessible(true);
            acceptingNew.set(null, true);
            Enchantment.registerEnchantment(GleamEnchantment.INSTANCE);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {

        } finally {
            try {
                acceptingNew.set(null, false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            acceptingNew.setAccessible(false);
        }
    }

    private void registerEventListeners(Listener... listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

}
