package dev.neuralnexus.onlyzombies;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SpawnGroupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Player and permission check
        String message;
        if (sender instanceof Player) {
            if (sender.hasPermission("oz.zspawngroup")) {
                if (args.length != 0) {
                    // get player's location
                    Player player = (Player) sender;
                    Location loc = player.getLocation();

                    int amount = Integer.parseInt(args[0]);
                    // Spawn a group of zombies
                    for (int i = 0; i < amount; i++) {
                        Objects.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ZOMBIE);
                    }
                    message = "§aSpawned a group of " + amount + " zombies!";
                } else {
                    message = "§cYou must specify an amount!";
                }
            } else {
                message = "§cYou do not have permission to use this command!";
            }
        } else {
            message = "§cYou must be a player to use this command!";
        }
        sender.sendMessage(message);
        return true;
    }
}