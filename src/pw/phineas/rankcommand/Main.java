package pw.phineas.rankcommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import pw.phineas.rankcommand.commands.Rank;
import java.io.IOException;

/**
 * Created by phineas on 30/01/2016.
 */
public class Main extends JavaPlugin {

    public void onEnable() {

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            getLogger().info("Failed to connect to MCStats! ;-;");
        }

        getCommand("rankset").setExecutor(new Rank(this));

        saveDefaultConfig();
    }

    private String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("rankreload")) {
            if(sender.hasPermission("rankcommand.reload")) {
                try {
                    reloadConfig();
                } finally {
                    sender.sendMessage(ChatColor.GREEN + "Successfully reloaded config file from disk!");
                }
            } else {
                sender.sendMessage(colorize(getConfig().getString("NoPermission")));
            }
        }
        return true;
    }

    public void onDisable() {

    }
}
