package pw.phineas.rankcommand.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pw.phineas.rankcommand.Main;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Created by phineas on 30/01/2016.
 */
public class Rank implements CommandExecutor {

    Main plugin;

    public Rank(Main plugin) {
        this.plugin = plugin;
    }

    private String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String prefix = colorize(this.plugin.getConfig().getString("Prefix"));
        if(cmd.getName().equalsIgnoreCase("rank")) {
            if(sender.hasPermission("rankcommand.use")) {
                if(args.length > 2 || args.length < 2) {
                    sender.sendMessage(prefix + colorize(this.plugin.getConfig().getString("WrongArgs")));
                    ((Player) sender).playSound(((Entity) sender).getLocation(), Sound.NOTE_BASS, 10, 1);
                }

                if(args.length == 2) {
                    Player tplayer = sender.getServer().getPlayer(args[0]);
                    String group = args[1];
                    PermissionGroup[] groups = { PermissionsEx.getPermissionManager().getGroup(group) };
                    PermissionsEx.getUser(tplayer).setGroups(groups);
                    sender.sendMessage(prefix + colorize(this.plugin.getConfig().getString("SetRank")).replaceAll("%player%", tplayer.getName()).replaceAll("%rank%", group));
                    tplayer.playSound(tplayer.getLocation(), Sound.NOTE_PIANO, 10, 1);
                    ((Player) sender).playSound(((Entity) sender).getLocation(), Sound.NOTE_PIANO, 10, 1);
                    tplayer.sendMessage(prefix + colorize(this.plugin.getConfig().getString("YourRankWasSet")).replaceAll("%rank%", group));
                }
            } else {
                sender.sendMessage(colorize(this.plugin.getConfig().getString("NoPermission")));
                ((Player) sender).playSound(((Entity) sender).getLocation(), Sound.NOTE_BASS, 10, 1);
            }
        }
        return false;
    }
}
