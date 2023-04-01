package dev.neuralnexus.onlyzombies;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class OnlyZombies extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.addDefault("enabled", true);

        // A map containing two sets of coordinates, one for each corner of the region
        config.addDefault("regions", new String[] {"world:0,0,0:0,0,0"});

        config.options().copyDefaults(true);
        saveConfig();

        // Initializing the event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean checkIfZombie(Entity entity) {
        FileConfiguration config = this.getConfig();

        // Check if the plugin is enabled and if the entity is a monster
        if (config.getBoolean("enabled") && entity instanceof Monster) {
            // If the entity is a zombie, return true
            return entity.getType() == EntityType.ZOMBIE;
        }
        return false;
    }

    public boolean checkIfInRegion(Entity entity) {
        FileConfiguration config = this.getConfig();

        // Get the regions from the config
        String[] regions = config.getStringList("regions").toArray(new String[0]);

        // Check if the entity is in any of the regions
        for (String region : regions) {
            String[] regionData = region.split(":");
            String worldName = regionData[0];
            String[] firstCorner = regionData[1].split(",");
            String[] secondCorner = regionData[2].split(",");

            // Check if the entity is in the world
            if (entity.getWorld().getName().equals(worldName)) {
                // Check if the entity is in the region
                if (entity.getLocation().getBlockX() >= Integer.parseInt(firstCorner[0]) && entity.getLocation().getBlockX() <= Integer.parseInt(secondCorner[0])) {
                    if (entity.getLocation().getBlockY() >= Integer.parseInt(firstCorner[1]) && entity.getLocation().getBlockY() <= Integer.parseInt(secondCorner[1])) {
                        if (entity.getLocation().getBlockZ() >= Integer.parseInt(firstCorner[2]) && entity.getLocation().getBlockZ() <= Integer.parseInt(secondCorner[2])) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();

        // Check if the entity is a monster
        if (entity instanceof Monster && !checkIfZombie(entity) && checkIfInRegion(entity)) {
            event.setCancelled(true);
        }
    }
}
