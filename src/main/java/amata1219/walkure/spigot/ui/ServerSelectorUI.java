package amata1219.walkure.spigot.ui;

import amata1219.niflheimr.dsl.InventoryUI;
import amata1219.niflheimr.dsl.component.Icon;
import amata1219.niflheimr.dsl.component.InventoryLayout;
import amata1219.niflheimr.dsl.component.InventoryLines;
import amata1219.niflheimr.event.InventoryUIClickEvent;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.config.ServerConfiguration;
import amata1219.walkure.spigot.data.processor.StringMeasurer;
import amata1219.walkure.spigot.data.serverdata.Server;
import amata1219.walkure.spigot.data.serverdata.ServerState;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.ChatColor;
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

        List<Server> openServers = groupedServers.getOrDefault(ServerState.OPEN, new ArrayList<>());
        openServers.addAll(groupedServers.getOrDefault(ServerState.OPEN_BETA, Collections.emptyList()));

        return build(InventoryLines.x6, l -> {
            for (int i = 0; i < openServers.size(); i++) {
                Server server = openServers.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(server));
                    s.onClick(createActionOnClick(viewer, server));
                }, i);
            }

            List<Server> systemServers = groupedServers.getOrDefault(ServerState.SYSTEM, Collections.emptyList());
            for (int i = 0; i < systemServers.size(); i++) {
                Server server = systemServers.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(server));
                    s.onClick(createActionOnClick(viewer, server));
                }, 9 * 2 + i);
            }

            List<Server> serversUnderDevelopment = groupedServers.getOrDefault(ServerState.DEVELOP, Collections.emptyList());
            for (int i = 0; i < serversUnderDevelopment.size(); i++) {
                Server server = serversUnderDevelopment.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(server));
                    s.onClick(createActionOnClick(viewer, server));
                }, 9 * 4 + i);
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

        String placeholder = "%separator%";

        List<String> lore = Arrays.stream(server.description.split("\\r?\\n"))
                .map(s -> WHITE + s)
                .collect(Collectors.toList());

        lore.add(0, "");

        lore.add("");

        int playerCount = serversToPlayerCounts.get(server.identifier)
            + server.childServers.stream()
            .mapToInt(child -> serversToPlayerCounts.getOrDefault(child, 0))
            .sum();
        String exclamationMarks = Stream.generate(() -> "！")
                .limit(playerCount / 30)
                .collect(Collectors.joining());

        if (playerCount > 0) lore.add(WHITE + "現在 " + GOLD + BOLD + playerCount + RESET + WHITE + " 人がプレイ中" + exclamationMarks);


        lore.add("");
        lore.add(placeholder);
        lore.add("");

        lore.add(WHITE + "状態 : " + server.state.text);
        if (server.recommendedVersion != null) lore.add(WHITE + "推奨バージョン : " + GOLD + server.recommendedVersion);
        lore.add(WHITE + "対応バージョン : " + GOLD + server.supportedVersions);

        lore.add("");

        int maxLength = lore.stream()
                .map(ChatColor::stripColor)
                .mapToInt(StringMeasurer::measure)
                .max()
                .getAsInt();

        String separator = DARK_GRAY + Stream.generate(() -> "-")
                .limit(maxLength)
                .collect(Collectors.joining());

        for (int i = 0; i < lore.size(); i++)
            if (lore.get(i).equals(placeholder)) lore.set(i, separator);

        return i -> {
            i.basedItemStack = server.icon.buildIconBase();
            i.displayName = "" + GOLD + BOLD + UNDERLINE + server.displayName;
            i.lore = lore;
            i.amount = Math.max(Math.min(playerCount, 64), 1);
            i.damage = server.icon.damage;
        };
    }

    private Consumer<InventoryUIClickEvent> createActionOnClick(Player viewer, Server server) {
        return event -> {
            Sound sound;
            if (server.identifier.equals("sclat")) sound = Sound.ENTITY_PLAYER_SWIM;
            else sound = Sound.UI_BUTTON_CLICK;

            viewer.playSound(viewer.getLocation(), sound, 1.0f, 1.0f);

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server.identifier);
            viewer.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
        };
    }

}
