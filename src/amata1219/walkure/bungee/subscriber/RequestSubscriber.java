package amata1219.walkure.bungee.subscriber;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import amata1219.redis.plugin.messages.common.RedisSubscriber;
import amata1219.redis.plugin.messages.common.io.ByteIO;
import amata1219.walkure.Channels;
import amata1219.walkure.bungee.Walkure;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.md_5.bungee.api.config.ServerInfo;

public class RequestSubscriber implements RedisSubscriber {

    private final Walkure plugin = Walkure.instance();
    private final RedisPluginMessagesAPI redis;

    public RequestSubscriber(RedisPluginMessagesAPI redis) {
        this.redis = redis;
    }

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput message) {
        ByteArrayDataOutput out = ByteIO.newDataOutput();
        out.writeUTF(networkInformation());
        redis.publisher().sendRedisMessage(Channels.RESPONSE, out);
    }

    private String networkInformation() {
        StringBuilder builder = new StringBuilder();
        for (ServerInfo server : plugin.getProxy().getServers().values()) {
            builder.append(',');
            builder.append(server.getName());
            builder.append(',');
            builder.append(server.getPlayers().size());
        }
        System.out.println("build: " + builder.substring(1));
        return builder.substring(1);
    }

}
