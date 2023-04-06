package dev.neuralnexus.onlyzombies;

import static dev.neuralnexus.onlyzombies.Utils.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntitySpawnListener implements Listener {
    private static final OnlyZombies plugin = OnlyZombies.getInstance();
    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (plugin.config.getAs("enabled", Boolean.class) // Check if the plugin is enabled
            && entity instanceof Monster // Check if the entity is a monster
            && !checkIfZombie(entity) // Check if the entity is not a zombie
            && checkIfInRegion(entity)) // Check if the entity is in the region
        {
            event.setCancelled(true);
        }
    }
}
