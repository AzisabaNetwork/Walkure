package amata1219.walkure.spigot.data;

import java.util.HashMap;

public class NetworkInformationBuilder {

    private final HashMap<String, Integer> playerCounts = new HashMap<>();

    public void accumulate(HashMap<String, Integer> part) {
        part.forEach((identifier, playerCount) -> {
            int newValue = (playerCounts.getOrDefault(identifier, 0)) + playerCount;
            playerCounts.put(identifier, newValue);
        });
    }

    public HashMap<String, Integer> build() {
        return playerCounts;
    }


}
