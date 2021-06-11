package me.gatogamer.unexpected.listener;

import me.gatogamer.unexpected.Unexpected;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {

            @Override
            public void run() {
                Unexpected.getInstance().getUserManager().getUserOrCreate(event.getPlayer().getUniqueId());

            }
        }.runTaskLater(Unexpected.getInstance(), 15L);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Unexpected.getInstance().getUserManager().deleteUser(event.getPlayer().getUniqueId());
    }
}
