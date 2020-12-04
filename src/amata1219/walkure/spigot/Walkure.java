package amata1219.walkure.spigot;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import amata1219.walkure.Channels;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Walkure extends JavaPlugin {

    private static Walkure plugin;

    private final RedisPluginMessagesAPI redis = (RedisPluginMessagesAPI) getServer().getPluginManager().getPlugin("RedisPluginMessagesAPI");

    @Override
    public void onEnable() {
        plugin = this;
        redis.registerIncomingChannels(Channels.RESPONSE);
        redis.registerOutgoingChannels(Channels.REQUEST, Channels.CONNECT);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public static Walkure instance() {
        return instance();
    }

}
