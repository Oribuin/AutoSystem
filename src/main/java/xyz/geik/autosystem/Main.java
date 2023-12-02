package xyz.geik.autosystem;

import de.leonhard.storage.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import xyz.geik.autosystem.handlers.AutoKillListener;
import xyz.geik.autosystem.handlers.KillEvent;
import xyz.geik.glib.GLibAPI;
import xyz.geik.glib.file.StorageAPI;

public class Main extends JavaPlugin {

    private Config config;

    public void onEnable() {
        new GLibAPI(this);
        this.config = new StorageAPI().initConfig("config");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new AutoKillListener(this), this);
        pm.registerEvents(new KillEvent(), this);
    }

    @NotNull
    public Config getConfigFile() {
        return config;
    }

}
