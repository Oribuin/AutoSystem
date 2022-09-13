package xyz.geik.autosystem.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.geik.autosystem.api.SpawnerKillEvent;

import java.util.Random;

public class KillEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void killEvent(SpawnerKillEvent event) {
        if (event.isCancelled())
            return;
        Entity entity = event.getEntity();
        EntityType entype = entity.getType();
        int amount = event.getEntityAmount();

        if (entity.getPassenger() != null) {
            ((Damageable) entity.getPassenger()).damage(1000.0);
            entity.getPassenger().remove();
        }
        else if (entype == EntityType.OCELOT || entype == EntityType.CHICKEN || entype == EntityType.COW
                || entype == EntityType.HORSE || entype == EntityType.MUSHROOM_COW || entype == EntityType.PIG
                || entype == EntityType.RABBIT || entype == EntityType.SHEEP || entype == EntityType.SQUID
                || entype == EntityType.WOLF || entype == EntityType.BAT || entype.toString().contains("DONKEY")
                || entype.toString().contains("MULE") || entype.toString().contains("LLAMA")
                || entype.toString().contains("PARROT") || entype.toString().contains("POLAR_BEAR")
                || entype.toString().contains("BEE") || entype.toString().contains("STRIDER")
                || entype.toString().contains("COD") || entype.toString().contains("FOX")
                || entype.toString().contains("PIGLIN") || entype.toString().contains("SALMON")
                || entype.toString().contains("TROPICALFISH") || entype.toString().contains("SKELETONHORSE")
                || entype.toString().contains("TURTLE") || entype.toString().contains("PANDA")
                || entype.toString().contains("DOLPHIN")) {
            int randomNum = 1 + (int) (Math.random() * 3.0);
            ((ExperienceOrb) entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class))
                    .setExperience(randomNum * amount);
        }
        else if (entype == EntityType.GUARDIAN || entype == EntityType.BLAZE) {
            ((ExperienceOrb) entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class)).setExperience(10 * amount);
            for (int i = 0; i < amount; i++)
                if (entype == EntityType.BLAZE && ((new Random()).nextInt(100)) <= 50)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.BLAZE_ROD, 1));
        }
        else if (entype == EntityType.IRON_GOLEM || entype == EntityType.SNOWMAN || entype.equals(EntityType.VILLAGER)
                || entype.toString().contains("WANDERINGTRADER"))
            return;
        else if (entype == EntityType.SLIME || entype == EntityType.MAGMA_CUBE)
            ((ExperienceOrb) entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class)).setExperience(3 * amount);
        else if (entype.toString().contains("PHANTOM")) {
            for (int i = 0; i < amount; i++)
                if (((new Random()).nextInt(100)) <= 50)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.PHANTOM_MEMBRANE, 1));
            ((ExperienceOrb) entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class)).setExperience(5 * amount);
        }
        else if (entype.toString().contains("SPIDER") || entype.toString().contains("CAVE")) {
            int nextRand = new Random().nextInt(100);
            for (int i = 0; i < amount; i++) {
                if (nextRand <= 33)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.STRING, 2));
                else if (nextRand <= 66)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.STRING, 1));

                if ((new Random()).nextInt(100) <= 20)
                    entity.getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.SPIDER_EYE, 1));
            }
            ((ExperienceOrb) entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class)).setExperience(5 * amount);
        }
        else
            ((ExperienceOrb) entity.getWorld().spawn(entity.getLocation(), ExperienceOrb.class)).setExperience(5 * amount);
    }

}
