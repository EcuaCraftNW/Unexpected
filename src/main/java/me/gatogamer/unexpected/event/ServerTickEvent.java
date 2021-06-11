package me.gatogamer.unexpected.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class ServerTickEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final long tick;

    public ServerTickEvent (long tick) {
        this.tick = tick;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}