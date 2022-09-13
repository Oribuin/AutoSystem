package xyz.geik.autosystem.handlers;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import xyz.geik.autosystem.Main;
import xyz.geik.autosystem.api.SpawnerKillEvent;

import java.util.List;

public class AutoKillListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void jockeyBlockerEvent(CreatureSpawnEvent e) {
        boolean feature = Main.getConfigFile().getBoolean("autoKill.feature");
        if (!feature)
            return;
        Entity en = e.getEntity();
        if (en instanceof Damageable)
            if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.MOUNT)
                    || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.JOCKEY))
                e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void spawnEvent(SpawnerSpawnEvent event) {
        if (!checks(event))
            return;

        Entity entity = event.getEntity();
        if (Main.getConfigFile().getBoolean("autoKill.autoCook"))
            entity.setFireTicks(20);

        if (wildStackerCheck(event))
            return;

        ((Damageable) entity).damage(1000.0);
        if (Main.getConfigFile().getBoolean("autoKill.removeMob"))
            entity.remove();

        Bukkit.getPluginManager().callEvent(new SpawnerKillEvent(entity, 1));
    }
    private boolean checks(SpawnerSpawnEvent e) {
        boolean feature = Main.getConfigFile().getBoolean("autoKill.feature");
        if (!feature)
            return false;

        List<String> worlds = Main.getConfigFile().getStringList("autoKill.allowedWorlds");
        if (!worlds.contains(e.getLocation().getWorld().getName()))
            return false;

        Entity entity = e.getEntity();
        if (!(entity instanceof Damageable))
            return false;

        EntityType entityType = e.getEntityType();

        if (Main.getConfigFile().contains("autoKill.allowedMobs")
                && !Main.getConfigFile().getStringList("autoKill.allowedMobs").contains(entityType))
            return false;

        if (Main.getConfigFile().contains("autoKill.blacklistMobs")
                && Main.getConfigFile().getStringList("autoKill.blacklistMobs").contains(entityType))
            return false;

        return true;
    }

    private boolean wildStackerCheck(SpawnerSpawnEvent e) {
        if (Bukkit.getPluginManager().getPlugin("WildStacker") != null) {
            if (!e.getEntityType().equals(EntityType.BLAZE)) {
                List<ItemStack> items = WildStackerAPI.getStackedEntity((LivingEntity) e.getEntity())
                        .getDrops(0);
                for (ItemStack item : items)
                    e.getEntity().getWorld().dropItemNaturally(e.getLocation(), item);

            }
            Bukkit.getPluginManager().callEvent(new SpawnerKillEvent(e.getEntity(),
                    WildStackerAPI.getStackedEntity((LivingEntity) e.getEntity()).getStackAmount()));
            WildStackerAPI.getStackedEntity((LivingEntity) e.getEntity()).remove();
            e.getSpawner().setDelay(-1);
            return true;
        }
        else return false;
    }
}