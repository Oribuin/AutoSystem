package xyz.geik.autosystem.handlers;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import com.bgsoftware.wildstacker.api.objects.StackedEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import xyz.geik.autosystem.Main;
import xyz.geik.autosystem.api.SpawnerKillEvent;

import java.util.List;

public class AutoKillListener implements Listener {

    private final Main plugin;

    public AutoKillListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void jockeyBlockerEvent(CreatureSpawnEvent e) {
        boolean feature = this.plugin.getConfigFile().getBoolean("autoKill.feature");
        if (!feature) return;

        SpawnReason spawnReason = e.getSpawnReason();
        if (spawnReason == SpawnReason.MOUNT || spawnReason == SpawnReason.JOCKEY) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void spawnEvent(SpawnerSpawnEvent event) {
        if (!this.checks(event))
            return;

        Entity entity = event.getEntity();
        if (!(entity instanceof Damageable)) return;
        Damageable damageable = (Damageable) entity;

        if (this.plugin.getConfigFile().getBoolean("autoKill.autoCook"))
            entity.setFireTicks(20);

        if (wildStackerCheck(event))
            return;

        if (this.plugin.getConfigFile().getBoolean("autoKill.removeMob")) {
            damageable.remove();
        } else {
            damageable.setHealth(0);
        }

        Bukkit.getPluginManager().callEvent(new SpawnerKillEvent(damageable, 1));
    }

    private boolean checks(SpawnerSpawnEvent e) {
        boolean feature = this.plugin.getConfigFile().getBoolean("autoKill.feature");
        if (!feature) return false;

        List<String> worlds = this.plugin.getConfigFile().getStringList("autoKill.allowedWorlds");
        if (!worlds.contains(e.getLocation().getWorld().getName()))
            return false;

        Entity entity = e.getEntity();
        if (!(entity instanceof LivingEntity)) return false;

        EntityType entityType = e.getEntityType();

        if (this.plugin.getConfigFile().getStringList("autoKill.allowedMobs").contains(entityType.name()))
            return false;

        return !this.plugin.getConfigFile().getStringList("autoKill.blacklistMobs").contains(entityType.name());
    }

    private boolean wildStackerCheck(SpawnerSpawnEvent e) {
        if (Bukkit.getPluginManager().getPlugin("WildStacker") == null) return false;
        if (!(e.getEntity() instanceof LivingEntity)) return false;

        LivingEntity entity = (LivingEntity) e.getEntity();

        if (e.getEntityType() != EntityType.BLAZE) {
            List<ItemStack> items = WildStackerAPI.getStackedEntity(entity).getDrops(0);
            items.forEach(item -> e.getEntity().getWorld().dropItemNaturally(e.getLocation(), item));
        }

        StackedEntity stackedEntity = WildStackerAPI.getStackedEntity(entity);

        Bukkit.getPluginManager().callEvent(new SpawnerKillEvent(e.getEntity(), stackedEntity.getStackAmount()));
        stackedEntity.remove();
        e.getSpawner().setDelay(-1);
        return true;
    }

}