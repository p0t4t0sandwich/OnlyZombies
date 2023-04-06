package dev.neuralnexus.onlyzombies;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.Map;

public class Utils {
    private static final OnlyZombies plugin = OnlyZombies.getInstance();

    // Check if the entity is a zombie
    public static boolean checkIfZombie(Entity entity) {
        // Check if the plugin is enabled and if the entity is a monster
        if (entity instanceof Monster) {
            // If the entity is a zombie, return true
            return entity.getType() == EntityType.ZOMBIE;
        }
        return false;
    }

    // Coords class
    public static class Coords {
        public int x;
        public int y;
        public int z;

        public Coords(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    // Region class
    public static class Region {
        public String world;
        public Coords pos1;
        public Coords pos2;

        public Region(String world, Coords pos1, Coords pos2) {
            this.world = world;
            this.pos1 = pos1;
            this.pos2 = pos2;
        }
    }

    // Get the region from the config
    public static Region getRegion(String regionName) {
        String worldName = plugin.config.getString("regions." + regionName + ".world");

        // Check if the region exists
        if (worldName == null) {
            return null;
        }

        // Get the coords
        int pos1X = plugin.config.getInt("regions." + regionName + ".pos1.x");
        int pos1Y = plugin.config.getInt("regions." + regionName + ".pos1.y");
        int pos1Z = plugin.config.getInt("regions." + regionName + ".pos1.z");
        int pos2X = plugin.config.getInt("regions." + regionName + ".pos2.x");
        int pos2Y = plugin.config.getInt("regions." + regionName + ".pos2.y");
        int pos2Z = plugin.config.getInt("regions." + regionName + ".pos2.z");

        // Return the region
        return new Region(worldName, new Coords(pos1X, pos1Y, pos1Z), new Coords(pos2X, pos2Y, pos2Z));
    }

    // Check if the entity is in the region
    public static boolean checkIfInRegion(Entity entity) {
        // Get the regions from the config
        Map<String, ?> regions = (Map<String, ?>) plugin.config.getBlock("regions").getStoredValue();

        if (regions == null) {
            return false;
        }

        // Check if the entity is in any of the regions
        for (Map.Entry<String, ?> entry : regions.entrySet()) {
            String regionName = entry.getKey();
            Region region = getRegion(regionName);

            // Check if the entity is in the world
            if (entity.getWorld().getName().equals(region.world)) {
                // Check if the entity is in the region
                int entityX = entity.getLocation().getBlockX();
                int entityY = entity.getLocation().getBlockY();
                int entityZ = entity.getLocation().getBlockZ();

                // Checks for the reverse min/max to account for dumb people
                if ((region.pos1.x <= entityX && entityX <= region.pos2.x)
                        || (region.pos1.x >= entityX && entityX >= region.pos2.x)) {
                    if ((region.pos1.y <= entityY && entityY <= region.pos2.y)
                            || (region.pos1.y >= entityY && entityY >= region.pos2.y)) {
                        if ((region.pos1.x <= entityZ && entityZ <= region.pos2.z)
                                || (region.pos1.z >= entityZ && entityZ >= region.pos2.z)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
