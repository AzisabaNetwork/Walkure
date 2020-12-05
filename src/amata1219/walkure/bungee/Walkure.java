package amata1219.walkure.bungee;

import amata1219.walkure.Channels;
import amata1219.walkure.bungee.listener.RequestReceiveListener;
import net.md_5.bungee.api.plugin.Plugin;

public class Walkure extends Plugin {

    private static Walkure instance;

    @Override
    public void onEnable() {
        instance = this;

        getProxy().registerChannel(Channels.BUNGEE_CORD);

        getProxy().getPluginManager().registerListener(this, new RequestReceiveListener());
    }

    @Override
    public void onDisable() {
        getProxy().unregisterChannel(Channels.BUNGEE_CORD);
    }

    public static Walkure instance() {
        return instance;
    }

}
