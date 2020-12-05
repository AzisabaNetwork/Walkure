package amata1219.walkure.spigot.data;

public class Server {

    public final String identifier;
    public final String recommendedVersion;
    public final String supportedVersions;
    public final ServerStatus status;
    public final String description;
    public final ServerIcon icon;

    public Server(
            String identifier,
            String recommendedVersion,
            String supportedVersions,
            ServerStatus status,
            String description,
            ServerIcon icon
    ) {
        this.identifier = identifier;
        this.recommendedVersion = recommendedVersion;
        this.supportedVersions = supportedVersions;
        this.status = status;
        this.description = description;
        this.icon = icon;
    }

}
