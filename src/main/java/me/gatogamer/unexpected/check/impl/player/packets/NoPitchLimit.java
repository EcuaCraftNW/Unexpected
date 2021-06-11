package me.gatogamer.unexpected.check.impl.player.packets;

import me.gatogamer.unexpected.check.Check;
import me.gatogamer.unexpected.user.User;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class NoPitchLimit extends Check {
    public NoPitchLimit(User user) {
        super(user, "No Pitch Limit", Arrays.asList("Previene que los jugadores puedan mover la dirección en una posición pitch>90"));
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        double pitch = event.getTo().getPitch();
        if (Math.abs(pitch) > 90) {
            fail(String.format("pitch=%.2f", pitch));
        }
    }
}
