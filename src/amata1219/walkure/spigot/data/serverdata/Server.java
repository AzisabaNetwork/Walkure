package amata1219.walkure.spigot.data.serverdata;

public class Server {

    public final String displayName;
    public final String recommendedVersion;
    public final String supportedVersions;
    public final ServerState status;
    public final String description;
    public final ServerIcon icon;

    public Server(
            String displayName,
            String recommendedVersion,
            String supportedVersions,
            ServerState status,
            String description,
            ServerIcon icon
    ) {
        this.displayName = displayName;
        this.recommendedVersion = recommendedVersion;
        this.supportedVersions = supportedVersions;
        this.status = status;
        this.description = description;
        this.icon = icon;
    }

}
