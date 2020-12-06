package amata1219.walkure.spigot.listener;

import amata1219.walkure.Channels;
import amata1219.walkure.spigot.Walkure;
import amata1219.walkure.spigot.registry.RequesterRegistry;
import amata1219.walkure.spigot.ui.ServerSelectorUI;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;

public class ResponseReceiveListener implements PluginMessageListener {

    private final Walkure plugin = Walkure.instance();
    private final RequesterRegistry registry;

    public ResponseReceiveListener(RequesterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onPluginMessageReceived(String tag, Player repeater, byte[] data) {
        if (!tag.equals(Channels.BUNGEE_CORD)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(data);
        if (!in.readUTF().equals(Channels.RESPONSE)) return;;

        long id = in.readLong();

        HashMap<String, Integer> networkInformation = new HashMap<>();
        int length = in.readInt();
        for (int i = 0; i < length; i += 2) networkInformation.put(in.readUTF(), in.readInt());

        Player requester = registry.requester(id);
        registry.unregister(id);

        HashMap<String, Integer> serversInformation = plugin.serverInformationSynthesizer().synthesize(networkInformation);
        new ServerSelectorUI(serversInformation).openInventory(requester);
    }

}
