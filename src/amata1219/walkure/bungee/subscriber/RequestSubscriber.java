package amata1219.walkure.bungee.subscriber;

import amata1219.redis.plugin.messages.common.RedisPluginMessagesAPI;
import amata1219.redis.plugin.messages.common.RedisSubscriber;
import amata1219.redis.plugin.messages.common.io.ByteIO;
import amata1219.walkure.Channels;
import amata1219.walkure.bungee.Walkure;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Collection;

public class RequestSubscriber implements RedisSubscriber {

    private final Walkure plugin = Walkure.instance();
    private final RedisPluginMessagesAPI redis;

    public RequestSubscriber(RedisPluginMessagesAPI redis) {
        this.redis = redis;
    }

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput message) {
        ByteArrayDataOutput out = ByteIO.newDataOutput();
        out.writeLong(message.readLong());
        Collection<ServerInfo> servers = plugin.getProxy().getServers().values();
        out.writeInt(servers.size());
        for (ServerInfo server : servers) {
            out.writeUTF(server.getName());
            out.writeInt(server.getPlayers().size());
        }
        redis.publisher().sendRedisMessage(Channels.RESPONSE, out);
    }

}
