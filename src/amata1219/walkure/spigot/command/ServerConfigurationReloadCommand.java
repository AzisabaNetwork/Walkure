package amata1219.walkure.spigot.command;

import amata1219.walkure.spigot.Walkure;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.ChatColor.*;

public class ServerConfigurationReloadCommand implements CommandExecutor {

    private final Walkure plugin = Walkure.instance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.serversConfig.reload();
        plugin.serverConfiguration.load();
        sender.sendMessage(GRAY + "Walkure :: servers.ymlのリロードが完了しました。");
        return true;
    }

}
