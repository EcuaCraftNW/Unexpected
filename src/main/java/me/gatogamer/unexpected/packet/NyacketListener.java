package me.gatogamer.unexpected.packet;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import io.github.retrooper.packetevents.event.impl.PostPacketPlayReceiveEvent;
import io.github.retrooper.packetevents.event.impl.PostPacketPlaySendEvent;
import me.gatogamer.unexpected.Unexpected;
import me.gatogamer.unexpected.event.ServerTickEvent;
import me.gatogamer.unexpected.user.User;
import me.gatogamer.unexpected.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class NyacketListener extends PacketListenerAbstract implements Listener {

    private final Unexpected unexpected;
    private final UserManager userManager;

    public NyacketListener(Unexpected unexpected) {
        this.unexpected = unexpected;
        this.userManager = unexpected.getUserManager();
    }

    @Override
    public void onPostPacketPlayReceive(PostPacketPlayReceiveEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());
        if (user != null) {
            user.getChecks().forEach(check -> check.onPostPacketPlayReceive(event));
        }
    }

    @Override
    public void onPostPacketPlaySend(PostPacketPlaySendEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());
        if (user != null) {
            user.getChecks().forEach(check -> check.onPostPacketPlaySend(event));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());
        if (user != null) {
            user.getChecks().forEach(check -> check.onMove(event));
        }
    }

    @EventHandler
    public void onMove(ServerTickEvent event) {
        userManager.getUsers().forEach((uuid, user) -> user.getChecks().forEach(check -> check.onTick(event)));
    }
}
