package amata1219.walkure.spigot.ui;

import amata1219.niflheimr.dsl.InventoryUI;
import amata1219.niflheimr.dsl.component.Icon;
import amata1219.niflheimr.dsl.component.InventoryLayout;
import amata1219.niflheimr.dsl.component.InventoryLines;
import amata1219.niflheimr.event.InventoryUIClickEvent;
import amata1219.walkure.spigot.Walkure;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.bukkit.ChatColor.*;

public class TemporaryUI implements InventoryUI {

    private static final List<SpecifiedTriple> SETTINGS;

    static {
        SETTINGS = Arrays.asList(
                new SpecifiedTriple(Material.NETHER_STAR, "Phantasy Gate", "casino"),
                new SpecifiedTriple(Material.BOW, "Leon Gun War", "lgw"),
                new SpecifiedTriple(Material.WHEAT, "Life", "life"),
                new SpecifiedTriple(Material.WATER_BUCKET, "Sclat", "sclat")
        );
    }

    @Override
    public InventoryLayout layout(Player viewer) {
        return build(InventoryLines.x1, l -> {
            for (int i = 0; i < SETTINGS.size(); i++) {
                SpecifiedTriple settings = SETTINGS.get(i);
                l.putSlot(s -> {
                    s.icon(createIconSettings(settings.material, settings.serverName));
                    s.onClick(createActionOnClick(viewer, settings.serverIdentifier));
                }, i);
            }
        });
    }

    private Consumer<Icon> createIconSettings(Material material, String serverName) {
        return i -> {
            i.material = material;
            i.displayName = "" + GOLD + BOLD + UNDERLINE + serverName;
        };
    }

    private Consumer<InventoryUIClickEvent> createActionOnClick(Player viewer, String serverIdentifier) {
        return e -> {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(serverIdentifier);
            viewer.sendPluginMessage(Walkure.instance(), "BungeeCord", out.toByteArray());
        };
    }

    private static class SpecifiedTriple {

        public final Material material;
        public final String serverName;
        public final String serverIdentifier;

        public SpecifiedTriple(Material material, String serverName, String serverIdentifier) {
            this.material = material;
            this.serverName = serverName;
            this.serverIdentifier = serverIdentifier;
        }

    }

}
