package amata1219.walkure.bungee;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import amata1219.walkure.Channels;
import amata1219.walkure.bungee.subscriber.ConnectSubscriber;
import amata1219.walkure.bungee.subscriber.RequestSubscriber;
import net.md_5.bungee.api.plugin.Plugin;

public class Walkure extends Plugin {

    private static Walkure instance;

    private final RedisPluginMessagesAPI redis = (RedisPluginMessagesAPI) getProxy().getPluginManager().getPlugin("RedisPluginMessagesAPI");

    @Override
    public void onEnable() {
        instance = this;
        redis.registerIncomingChannels(Channels.REQUEST, Channels.CONNECT);
        redis.registerSubscriber(Channels.REQUEST, new RequestSubscriber(redis));
        redis.registerSubscriber(Channels.CONNECT, new ConnectSubscriber());
        redis.registerOutgoingChannels(Channels.RESPONSE);
    }

    @Override
    public void onDisable() {

    }

    public static Walkure instance() {
        return instance;
    }

}
