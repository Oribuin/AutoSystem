package xyz.geik.autosystem.api;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpawnerKillEvent extends Event {

    private boolean isCancelled;
    private Entity entity;
    private int entityAmount;

    public SpawnerKillEvent(Entity entity, int entityAmount) {
        this.entity = entity;
        this.entityAmount = entityAmount;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean arg0) {
        this.isCancelled = arg0;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getEntityAmount() {
        return entityAmount;
    }
}
