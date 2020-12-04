package amata1219.walkure.bungee.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import amata1219.walkure.bungee.Walkure;
import com.google.common.io.ByteArrayDataInput;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class ConnectSubscriber implements RedisSubscriber {

    private final Walkure walkure = Walkure.instance();

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput message) {
        UUID playerUniqueId = UUID.fromString(message.readUTF());
        ProxiedPlayer player = walkure.getProxy().getPlayer(playerUniqueId);
        if (player == null) throw new IllegalArgumentException("player whose uuid is " + playerUniqueId + " is not found");

        String serverName = message.readUTF();
        ServerInfo server = walkure.getProxy().getServerInfo(serverName);
        if (server == null) throw new IllegalArgumentException(serverName + " server is not found");

        player.connect(server);
    }

}
