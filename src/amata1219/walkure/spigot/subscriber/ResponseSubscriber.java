package amata1219.walkure.spigot.subscriber;

import amata1219.redis.plugin.messages.common.RedisSubscriber;
import com.google.common.io.ByteArrayDataInput;

import java.util.HashMap;

public class ResponseSubscriber implements RedisSubscriber {

    @Override
    public void onRedisMessageReceived(String sourceServerName, ByteArrayDataInput message) {
        HashMap<String, Integer> networkInformation = new HashMap<>();
        int length = message.readInt();
        for (int i = 0; i < length; i += 2)
            networkInformation.put(message.readUTF(), message.readInt());
    }

}
