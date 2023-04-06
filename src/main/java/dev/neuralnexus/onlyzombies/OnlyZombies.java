package dev.neuralnexus.onlyzombies;

import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class OnlyZombies extends JavaPlugin {
    // Config
    public YamlDocument config;

    // Singleton instance
    private static OnlyZombies instance;
    public static OnlyZombies getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Config
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"));
            config.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initializing the singleton instance
        instance = this;

        // Registering the commands
        getCommand("zspawngroup").setExecutor(new SpawnGroupCommand());
        getCommand("zspawnhorde").setExecutor(new SpawnHordeCommand());

        // Initializing the event listener
        getServer().getPluginManager().registerEvents(new EntitySpawnListener(), this);

        // Plugin enable message
        getLogger().info("OnlyZombies has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin disable message
        getLogger().info("OnlyZombies has been disabled!");
    }
}
