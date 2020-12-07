package amata1219.walkure.bungee.listener;

import amata1219.walkure.Channels;
import amata1219.walkure.bungee.Walkure;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.ServerConnection;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RequestReceiveListener implements Listener {

    private final Walkure plugin = Walkure.instance();

    @EventHandler
    public void on(PluginMessageEvent event) {
        if (!event.getTag().equals(Channels.BUNGEE_CORD)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        if (!in.readUTF().equals(Channels.REQUEST)) return;;

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(Channels.RESPONSE);
        out.writeLong(in.readLong());

        Collection<ServerInfo> servers = plugin.getProxy().getServers().values();
        out.writeInt(servers.size());
        for (ServerInfo server : servers) {
            out.writeUTF(server.getName());
            out.writeInt(server.getPlayers().size());
        }

        ((ServerConnection) event.getSender()).sendData(Channels.BUNGEE_CORD, out.toByteArray());
    }

}
