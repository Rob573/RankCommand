package pw.phineas.rankcommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        if(cmd.getName().equalsIgnoreCase("rankset")) {
            if(sender.hasPermission("rankcommand.use")) {
                if(args.length > 2 || args.length < 2) {
                    sender.sendMessage(prefix + colorize(this.plugin.getConfig().getString("WrongArgs")));
                }

                if(args.length == 2) {
                    String tplayer;

                    try {
                        tplayer = Bukkit.getPlayer(args[0]).getName();
                    } catch (Exception e) {
                        sender.sendMessage(prefix + ChatColor.GRAY + "That player is not online!");
                        return false;
                    }

                    Player target = Bukkit.getPlayer(tplayer);

                    String group = args[1];
                    PermissionGroup[] groups = { PermissionsEx.getPermissionManager().getGroup(group) };
                    PermissionsEx.getUser(target).setGroups(groups);
                    sender.sendMessage(prefix + colorize(this.plugin.getConfig().getString("SetRank")).replaceAll("%player%", target.getName()).replaceAll("%rank%", group));
                    target.sendMessage(prefix + colorize(this.plugin.getConfig().getString("YourRankWasSet")).replaceAll("%rank%", group));

                    if(this.plugin.getConfig().getBoolean("StaffMessage.enabled")) {
                        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("rankcommand.msg")).forEach(player -> {
                            player.sendMessage(prefix + colorize(this.plugin.getConfig().getString("StaffMessage.message")).replaceAll("%player%", sender.getName()).replaceAll("%playerchanged%", target.getName()).replaceAll("%rankset%",group));
                        });
                    }
                }
            } else {
                sender.sendMessage(colorize(this.plugin.getConfig().getString("NoPermission")));
            }
        }
        return false;
    }
}
