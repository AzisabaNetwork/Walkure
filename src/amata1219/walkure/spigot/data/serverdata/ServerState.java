package amata1219.walkure.spigot.data.serverdata;

import static org.bukkit.ChatColor.*;

public enum ServerState {

    OPEN(GOLD + "公開中"),
    OPEN_BETA(GOLD + "ベータ公開中"),
    SYSTEM(DARK_GRAY + "システム"),
    DEVELOP(DARK_GRAY + "開発中");

    public final String text;

    private ServerState(String text) {
        this.text = text;
    }

}
