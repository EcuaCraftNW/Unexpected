package me.gatogamer.unexpected.check.impl.player.combat;

import me.gatogamer.unexpected.check.Check;
import me.gatogamer.unexpected.user.User;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Created on 10/24/2020 Package com.gladurbad.medusa.check.impl.combat.aim by GladUrBad
 */
public class AimAssistA extends Check {
    public AimAssistA(User user) {
        super(user, "AimAssist (A)", Arrays.asList("Checks for invalid yaw/pitch movements"));
    }

    private int buffer = 0;

    private final Predicate<Float> rotation = rotation -> rotation > 3F && rotation < 35F;


    @Override
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        float deltaPitch = Math.abs(event.getTo().getPitch() - event.getFrom().getPitch());
        float deltaYaw = Math.abs((event.getTo().getYaw() - event.getFrom().getYaw()) % 360F);

        boolean isInvalidPitch = deltaPitch < 0.009 && rotation.test(deltaYaw);
        boolean isInvalidYaw = deltaYaw < 0.009 && rotation.test(deltaPitch);

        if (!player.isInsideVehicle()) {
            if ((isInvalidPitch || isInvalidYaw) && event.getTo().getPitch() < 89F) {
                if (buffer++ > 20) {
                    fail(String.format("deltaYaw=%.2f, deltaPitch=%.2f", deltaYaw, deltaPitch));
                }
            } else {
                buffer = Math.max(buffer-1, 0);
            }
        }
    }
}
