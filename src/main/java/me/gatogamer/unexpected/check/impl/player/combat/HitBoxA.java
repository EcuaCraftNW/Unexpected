package me.gatogamer.unexpected.check.impl.player.combat;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.impl.PostPacketPlaySendEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.gatogamer.unexpected.Unexpected;
import me.gatogamer.unexpected.check.Check;
import me.gatogamer.unexpected.event.ServerTickEvent;
import me.gatogamer.unexpected.user.User;
import me.gatogamer.unexpected.utils.BoundingBox;
import me.gatogamer.unexpected.utils.EvictingList;
import me.gatogamer.unexpected.utils.Pair;
import me.gatogamer.unexpected.utils.RayTrace;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.util.NumberConversions;

import java.util.Arrays;

/**
 * Created on 10/26/2020 Package com.gladurbad.medusa.check.impl.combat.reach by GladUrBad
 */
public class HitBoxA extends Check {
    public HitBoxA(User user) {
        super(user, "HitBox (A)", Arrays.asList("Verifies the angle of attack."));
    }

    private int buffer = 0;

    private Entity target;
    private Entity lastTarget;

    private final EvictingList<Pair<Location, Long>> targets = new EvictingList<>(40);

    @Override
    public void onPostPacketPlaySend(PostPacketPlaySendEvent event) {
        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(event.getNMSPacket());

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK && wrapper.getEntity() != null) {
                lastTarget = target == null ? wrapper.getEntity() : target;
                target = wrapper.getEntity();

                if (getUser().getPlayer().getGameMode() != GameMode.SURVIVAL
                        || !(target instanceof Player || target instanceof Villager)
                        || target != lastTarget
                        || !targets.isFull()) return;

                int ticks = Unexpected.getInstance().getServerTickTask().getTicks();
                int pingTicks = NumberConversions.floor(PacketEvents.get().getPlayerUtils().getPing(getUser().getPlayer()) / 50.0) + 3;

                RayTrace rayTrace = new RayTrace(getUser().getPlayer());

                final int collided = (int) targets.stream()
                        .filter(pair -> Math.abs(ticks - pair.getY() - pingTicks) < 3)
                        .filter(pair -> {
                            final Location location = pair.getX();
                            final BoundingBox boundingBox = new BoundingBox(location.getX() - 0.4, location.getX() + 0.4, location.getY(), location.getY() + 1.9, location.getZ() - 0.4, location.getZ() + 0.4);

                            return boundingBox.collidesD(rayTrace, 0, 6) != 10;
                        }).count();

                if (collided <= 2) {
                    if (++buffer > 10) {
                        fail("collided=" + collided + " buffer=" + buffer);
                    }
                } else {
                    buffer -= buffer > 0 ? 1 : 0;
                }
            }
        }
    }

    @Override
    public void onTick(ServerTickEvent event) {
        if (target != null && lastTarget != null) {
            if (target != lastTarget) {
                targets.clear();
            }
            targets.add(new Pair<>(target.getLocation(), event.getTick()));
        }
    }
}
