package amata1219.walkure.spigot.ui;

import amata1219.niflheimr.dsl.InventoryUI;
import amata1219.niflheimr.dsl.component.Icon;
import amata1219.niflheimr.dsl.component.InventoryLayout;
import amata1219.niflheimr.dsl.component.InventoryLines;
import amata1219.niflheimr.event.InventoryUIClickEvent;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.config.ServerConfiguration;
import amata1219.walkure.spigot.data.serverdata.Server;
import amata1219.walkure.spigot.data.serverdata.ServerState;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.ChatColor.*;

public class ServerSelectorUI implements InventoryUI {

    private final Walkure plugin = Walkure.instance();
    private final ServerConfiguration config = plugin.serverConfiguration();
    private final HashMap<String, Integer> serversToPlayerCounts;

    public ServerSelectorUI(HashMap<String, Integer> serversToPlayerCounts) {
        this.serversToPlayerCounts = serversToPlayerCounts;
    }

    @Override
    public InventoryLayout layout(Player viewer) {
        Map<ServerState, List<Server>> groupedServers = serversToPlayerCounts.keySet().stream()
                .map(config.servers::get)
                .collect(Collectors.groupingBy(s -> s.state));

        List<Server> openServers = groupedServers.get(ServerState.OPEN);
        openServers.addAll(groupedServers.get(ServerState.OPEN_BETA));

        return build(InventoryLines.x6, l -> {
            for (int i = 0; i < openServers.size(); i++) {
                Server server = openServers.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(server));
                    s.onClick(createActionOnClick(viewer, server));
                }, i);
            }

            List<Server> systemServers = groupedServers.get(ServerState.SYSTEM);
            for (int i = 9 * 2; i < systemServers.size(); i++) {
                Server server = systemServers.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(server));
                    s.onClick(createActionOnClick(viewer, server));
                }, i);
            }

            List<Server> serversUnderDevelopment = groupedServers.get(ServerState.DEVELOP);
            for (int i = 9 * 4; i < serversUnderDevelopment.size(); i++) {
                Server server = serversUnderDevelopment.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(server));
                    s.onClick(createActionOnClick(viewer, server));
                }, i);
            }
        });
    }

    private Consumer<Icon> createIconSettings(Server server) {
        /*
            テキストに使用するカラーコードは678f(GOLD, GRAY, DARK_GRAY, WHITE)の冬を意識した4色にする
            GOLD → 値
            GRAY → 文章
            DARK_GRAY → 値
            WHITE → 破線
            字体を変更するコードは自由に使用可能
            破線の長さはリストの中の最長文字列に合わせる
            %dashed-line% → 破線に置換
            文章の構造を直感的に把握出来るように改行は明示する
         */

        String placeholder = "%dashed-line%";

        List<String> lore = Arrays.stream(server.description.split("\\r?\\n"))
                .map(s -> GRAY + s)
                .collect(Collectors.toList());

        int playerCount = serversToPlayerCounts.get(server.identifier);
        String exclamationMarks = Stream.generate(() -> "！")
                .limit(playerCount / 30)
                .collect(Collectors.joining());

        lore.add("");
        lore.add(placeholder);
        lore.add("");

        lore.add(GRAY + "状態 → " + server.state.text);
        lore.add(GRAY + "現在 " + GOLD + playerCount + GRAY + " 人がプレイ中" + exclamationMarks);

        lore.add("");
        lore.add(placeholder);
        lore.add("");

        if (server.recommendedVersion != null) lore.add(GRAY + "推奨バージョン → " + GOLD + server.recommendedVersion);
        lore.add(GRAY + "対応バージョン → " + GOLD + server.supportedVersions);

        String longestLine = lore.stream().max(Comparator.comparing(String::length)).get();
        String dashedLine = Stream.generate(() -> "-")
                .limit(longestLine.length())
                .collect(Collectors.joining());

        for (int i = 0; i < lore.size(); i++)
            if (lore.get(i).equals(placeholder)) lore.set(i, dashedLine);

        return i -> {
            i.basedItemStack = server.icon.buildIconBase();
            i.displayName = GOLD + "" + UNDERLINE + server.displayName;
            i.lore = lore;
        };
    }

    private Consumer<InventoryUIClickEvent> createActionOnClick(Player viewer, Server server) {
        return event -> {
            Sound sound;
            if (server.identifier.equals("sclat")) sound = Sound.BLOCK_WATER_AMBIENT;
            else sound = Sound.UI_BUTTON_CLICK;

            viewer.playSound(viewer.getLocation(), sound, 1.0f, 1.0f);

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server.identifier);
            viewer.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        };
    }

}
