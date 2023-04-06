package dev.neuralnexus.onlyzombies;

import static dev.neuralnexus.onlyzombies.Utils.*;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Random;

public class SpawnHordeCommand implements CommandExecutor {
    private final OnlyZombies plugin = OnlyZombies.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Player and permission check
        String message;
        if (sender instanceof Player && !sender.hasPermission("oz.zspawnhorde")) {
            message = "§cYou do not have permission to use this command!";
        } else {
            if (args.length != 0) {
                Region region = getRegion(args[0]);
                if (region == null) {
                    message = "§cThat region does not exist!";
                } else {
                    // pick a random location in the region
                    World world = plugin.getServer().getWorld(region.world);
                    // random number between 0 and 1
                    Random random = new Random();
                    double xMult = random.nextDouble();
                    double yMult = random.nextDouble();
                    double zMult = random.nextDouble();

                    int x = (int) (region.pos1.x + (region.pos2.x - region.pos1.x) * xMult);
                    int y = (int) (region.pos1.y + (region.pos2.y - region.pos1.y) * yMult);
                    int z = (int) (region.pos1.z + (region.pos2.z - region.pos1.z) * zMult);

                    Location loc = new Location(world, x, y, z);


                    // Spawn a horde of zombies
                    for (int i = 0; i < region.hoardSize; i++) {
                        assert world != null;
                        world.spawnEntity(loc, EntityType.ZOMBIE);
                    }

                    message = "§aSpawned a horde of zombies in the region " + args[0] + "!";
                }
            } else {
                message = "§cYou must specify a region!";
            }
        }
        sender.sendMessage(message);
        return true;
    }
}
