package amata1219.walkure.spigot.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.data.NetworkInformationBuilder;
import amata1219.walkure.spigot.registry.RequesterRegistry;
import com.google.common.io.ByteArrayDataInput;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ResponseSubscriber implements RedisSubscriber {

    private final Walkure plugin = Walkure.instance();
    private final RequesterRegistry registry;

    private final HashMap<Long, Integer> responsesRequired = new HashMap<>();
    private final HashMap<Long, NetworkInformationBuilder> networkInformationBuilders = new HashMap<>();

    public ResponseSubscriber(RequesterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput message) {
        HashMap<String, Integer> partialNetworkInformation = new HashMap<>();
        long id = message.readLong();
        int length = message.readInt();
        for (int i = 0; i < length; i += 2)
            partialNetworkInformation.put(message.readUTF(), message.readInt());

        if (!responsesRequired.containsKey(id)) return;

        int necessaryResponses = responsesRequired.get(id);
        if (necessaryResponses > 1) {
            responsesRequired.put(id, necessaryResponses - 1);
            networkInformationBuilders.get(id).accumulate(partialNetworkInformation);
            return;
        }

        responsesRequired.remove(id);
        HashMap<String, Integer> networkInformation = networkInformationBuilders.remove(id).build();
        Player requester = registry.requester(id);
        registry.unregister(id);

        HashMap<String, Integer> serversInformation = plugin.serverInformationSynthesizer.synthesize(networkInformation);

    }

}
