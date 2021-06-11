package me.gatogamer.unexpected.task;

import me.gatogamer.unexpected.Unexpected;
import me.gatogamer.unexpected.event.ServerTickEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.LongAccumulator;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ServerTickTask extends BukkitRunnable {

    private final LongAccumulator ticks = new LongAccumulator(Long::sum, 0L);

    @Override
    public void run() {
        ticks.accumulate(1L);

        Bukkit.getPluginManager().callEvent(new ServerTickEvent(ticks.get()));
    }

    public int getTicks() {
        return Math.toIntExact(ticks.get());
    }
}
