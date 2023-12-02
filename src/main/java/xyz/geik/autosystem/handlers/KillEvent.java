package xyz.geik.autosystem.handlers;

import org.bukkit.Material;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Golem;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.geik.autosystem.api.SpawnerKillEvent;

import java.util.Random;

public class KillEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSpawnerKill(SpawnerKillEvent event) {
        Entity entity = event.getEntity();
        EntityType entityType = entity.getType();
        int amount = event.getEntityAmount();

        entity.getPassengers().forEach(Entity::remove);

        if (entity instanceof Animals) {
            int randomNum = 1 + (int) (Math.random() * 3.0);
            entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class, x -> x.setExperience(randomNum * amount));
            return;
        }

        // Villagers and golems drop nothing
        if (entity instanceof Golem || entity instanceof AbstractVillager) {
            return;
        }

        /// Guardian & Blaze Rods
        if (entityType == EntityType.GUARDIAN || entityType == EntityType.BLAZE) {
            entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class, x -> x.setExperience(10 * amount));

            if (entityType == EntityType.BLAZE) {
                ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD, 1);
                for (int i = 0; i < amount; i++) {
                    if ((new Random()).nextInt(100) <= 50)
                        continue;

                    entity.getWorld().dropItemNaturally(entity.getLocation(), blazeRod.clone());
                }
            }

            return;
        }

        // Slime & Magma Cubes
        if (entity instanceof Slime) {
            entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class, x -> x.setExperience(3 * amount));
            return;
        }

        if (entityType == EntityType.PHANTOM) {
            final ItemStack phantomMembrane = new ItemStack(Material.PHANTOM_MEMBRANE, 1);
            for (int i = 0; i < amount; i++) {
                if ((new Random()).nextInt(100) <= 50)
                    continue;

                entity.getWorld().dropItemNaturally(entity.getLocation(), phantomMembrane.clone());
            }

            entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class, x -> x.setExperience(5 * amount));
            return;
        }

        if (entity instanceof Spider) {
            int nextRand = new Random().nextInt(100);
            for (int i = 0; i < amount; i++) {
                if (nextRand <= 33)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.STRING, 2));
                else if (nextRand <= 66)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.STRING, 1));

                if ((new Random()).nextInt(100) <= 20)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.SPIDER_EYE, 1));
            }

            entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class, x -> x.setExperience(5 * amount));
            return;
        }

        entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class, x -> x.setExperience(5 * amount));
    }

}
