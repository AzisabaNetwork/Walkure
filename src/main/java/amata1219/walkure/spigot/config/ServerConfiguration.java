package amata1219.walkure.spigot.config;

import amata1219.walkure.spigot.data.serverdata.Server;
import amata1219.walkure.spigot.data.serverdata.ServerIcon;
import amata1219.walkure.spigot.data.serverdata.ServerState;
import amata1219.walkure.spigot.data.serverdata.SkullIcon;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;

public class ServerConfiguration {

    private final Yaml yaml;

    public final HashMap<String, Server> servers = new HashMap<>();
    public final HashMap<String, String> childrenToParents = new HashMap<>();

    public ServerConfiguration(Yaml yaml) {
        this.yaml = yaml;
    }

    public void load() {
        servers.clear();
        childrenToParents.clear();

        ConfigurationSection serversSection = yaml.config().getConfigurationSection("servers");
        for (String serverIdentifier : serversSection.getKeys(false)) {
            ConfigurationSection section = serversSection.getConfigurationSection(serverIdentifier);
            String displayName = section.getString("display-name");
            String recommendedVersion = section.getString("versions.recommended");
            String supportedVersions = section.getString("versions.supported");
            ServerState state = ServerState.valueOf(section.getString("state"));
            Material material = Material.valueOf(section.getString("item-material"));
            short damage = (short) section.getInt("item-damage", 0);
            String skullTexture = section.getString("item-skull-texture");
            String description = section.getString("description");
            ServerIcon icon = skullTexture == null ? new ServerIcon(material, damage) : new SkullIcon(skullTexture);
            List<String> childServers = section.getStringList("child-servers");
            Server server = new Server(serverIdentifier, displayName, recommendedVersion, supportedVersions, state, description, icon, childServers);
            servers.put(serverIdentifier, server);
            childServers.forEach(childServerIdentifier -> childrenToParents.put(childServerIdentifier, serverIdentifier));
        }
    }

}
