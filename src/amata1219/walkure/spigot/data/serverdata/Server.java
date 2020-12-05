package amata1219.walkure.spigot.data.serverdata;

public class Server {

    public final String identifier;
    public final String displayName;
    public final String recommendedVersion;
    public final String supportedVersions;
    public final ServerState state;
    public final String description;
    public final ServerIcon icon;

    public Server(
            String identifier,
            String displayName,
            String recommendedVersion,
            String supportedVersions,
            ServerState state,
            String description,
            ServerIcon icon
    ) {
        this.identifier = identifier;
        this.displayName = displayName;
        this.recommendedVersion = recommendedVersion;
        this.supportedVersions = supportedVersions;
        this.state = state;
        this.description = description;
        this.icon = icon;
    }

}
