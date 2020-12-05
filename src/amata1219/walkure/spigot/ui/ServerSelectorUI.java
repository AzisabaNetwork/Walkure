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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.bukkit.ChatColor.*;

public class ServerSelectorUI implements InventoryUI {

    private final Walkure plugin = Walkure.instance();
    private final ServerConfiguration config = plugin.serverConfiguration;
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
                    s.onClick(createActionOnClick(viewer, server));
                }, i);
            }
        });
    }

    private Consumer<Icon> createIconSettings(Server server) {
        return i -> {
            i.displayName = AQUA + server.displayName;

            /*
            lore = mutableListOf<String>().apply {
            if players > 0
              add("${GRAY}現在 ${YELLOW}$players${GRAY} 人がプレイ中")

                    add("${RESET}このサーバーは $state${RESET} です。")
                            append("${GRAY}バージョン ${GOLD}${serverConfiguration.recommendedVersion}${GRAY} 推奨")
                            append("> ${GOLD}${serverConfiguration.supportedVersions}${GRAY} にも対応")
                    add("${BLACK}${serverIdentifier.identifier}")
                }
            }
             */
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
