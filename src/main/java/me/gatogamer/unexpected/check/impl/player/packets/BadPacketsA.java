package me.gatogamer.unexpected.check.impl.player.packets;

import io.github.retrooper.packetevents.event.impl.PostPacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
import io.github.retrooper.packetevents.packetwrappers.play.in.blockdig.WrappedPacketInBlockDig;
import io.github.retrooper.packetevents.packetwrappers.play.in.useentity.WrappedPacketInUseEntity;
import me.gatogamer.unexpected.check.Check;
import me.gatogamer.unexpected.user.User;

import java.util.Arrays;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class BadPacketsA extends Check {
    private boolean blocking = false;
    private int buffer = 0;

    public BadPacketsA(User user) {
        super(user, "BadPackets (A)", Arrays.asList("Validación del paquete dig"));
    }

    @Override
    public void onPostPacketPlayReceive(PostPacketPlayReceiveEvent event) {
        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(event.getNMSPacket());

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                if (blocking) {
                    if (++buffer > 5) {
                        fail("b=true buffer="+buffer);
                    }
                } else {
                    buffer = 0;
                }
            }
        } else if (event.getPacketId() == PacketType.Play.Client.BLOCK_DIG) {
            WrappedPacketInBlockDig wrapper = new WrappedPacketInBlockDig(event.getNMSPacket());

            if (wrapper.getDigType().equals(WrappedPacketInBlockDig.PlayerDigType.RELEASE_USE_ITEM)) {
                blocking = true;
            }
        } else if (event.getPacketId() == PacketType.Play.Client.FLYING) {
            blocking = false;
        }
    }
}
