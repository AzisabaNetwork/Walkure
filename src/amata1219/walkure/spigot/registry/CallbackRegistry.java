package amata1219.walkure.spigot.registry;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CallbackRegistry {

    private final HashMap<Long, Player> waiters = new HashMap<>();

    public boolean hasWaiter(long id) {
        return waiters.containsKey(id);
    }

    public Player getPlayer(long id) {
        return waiters.remove(id);
    }

}
