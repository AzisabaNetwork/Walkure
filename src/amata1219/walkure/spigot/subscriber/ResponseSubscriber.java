package amata1219.walkure.spigot.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.registry.RequesterRegistry;
import amata1219.walkure.spigot.ui.ServerSelectorUI;
import com.google.common.io.ByteArrayDataInput;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ResponseSubscriber implements RedisSubscriber {

    private final Walkure plugin = Walkure.instance();
    private final RequesterRegistry registry;

    public ResponseSubscriber(RequesterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput message) {
        long id = message.readLong();

        HashMap<String, Integer> networkInformation = new HashMap<>();
        int length = message.readInt();
        for (int i = 0; i < length; i += 2) networkInformation.put(message.readUTF(), message.readInt());

        Player requester = registry.requester(id);
        registry.unregister(id);

        HashMap<String, Integer> serversInformation = plugin.serverInformationSynthesizer.synthesize(networkInformation);
        new ServerSelectorUI(serversInformation).openInventory(requester);
    }

}
