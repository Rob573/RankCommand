package pw.phineas.rankcommand;

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

        getCommand("rank").setExecutor(new Rank(this));

        saveDefaultConfig();
    }

    public void onDisable() {

    }
}
