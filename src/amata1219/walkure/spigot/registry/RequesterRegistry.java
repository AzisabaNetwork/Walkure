package amata1219.walkure.spigot.registry;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class RequesterRegistry {

    private final HashMap<Long, Player> requesters = new HashMap<>();

    public void register(long id, Player requester) {
        requesters.put(id, requester);
    }

    public Player requester(long id) {
        return requesters.get(id);
    }

    public void unregister(long id) {
        requesters.remove(id);
    }


}
