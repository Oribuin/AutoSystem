package xyz.geik.autosystem;

import de.leonhard.storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.geik.autosystem.handlers.AutoKillListener;
import xyz.geik.autosystem.handlers.KillEvent;
import xyz.geik.glib.GLibAPI;
import xyz.geik.glib.file.StorageAPI;

public class Main extends JavaPlugin {

    private static Main instance;
    private static Config config;

    public void onEnable() {
        instance = this;
        new GLibAPI(this);
        config = new StorageAPI().initConfig("config");
        registerEvents();
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AutoKillListener(), this);
        pm.registerEvents(new KillEvent(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public static Config getConfigFile() {
        return config;
    }
}
