package amata1219.walkure.spigot.data;

public enum ServerState {

    OPEN("公開中"),
    OPEN_BETA("ベータ公開中"),
    SYSTEM("システム"),
    DEVELOP("開発中");

    public final String text;

    private ServerState(String text) {
        this.text = text;
    }

}
