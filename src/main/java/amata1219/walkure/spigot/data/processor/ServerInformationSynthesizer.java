package amata1219.walkure.spigot.data.processor;

import amata1219.walkure.spigot.config.ServerConfiguration;
import amata1219.walkure.spigot.data.Pair;

import java.util.HashMap;

public class ServerInformationSynthesizer {

    private final ServerConfiguration config;

    public ServerInformationSynthesizer(ServerConfiguration config) {
        this.config = config;
    }

    public HashMap<String, Integer> synthesize(HashMap<String, Integer> networkInformation) {
        HashMap<String, Integer> serversInformation = new HashMap<>();

        Pair<HashMap<String, Integer>, HashMap<String, Integer>> split = MapSplitter.split(networkInformation, config.servers::containsKey);
        HashMap<String, Integer> parents = split.right, children = split.left;

        parents.forEach(serversInformation::put);

        children.forEach((serverIdentifier, playerCount) -> {
            if (!(serverIdentifier.startsWith("sclat") || config.childrenToParents.containsKey(serverIdentifier))) return;

            String parentServerIdentifier;
            if (serverIdentifier.startsWith("sclat")) parentServerIdentifier = "sclat";
            else parentServerIdentifier = config.childrenToParents.get(serverIdentifier);

            serversInformation.put(parentServerIdentifier, serversInformation.get(parentServerIdentifier) + playerCount);
        });

        return serversInformation;
    }

}
