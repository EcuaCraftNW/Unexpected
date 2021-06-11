package me.gatogamer.unexpected.check.impl.player.packets;

import io.github.retrooper.packetevents.event.impl.PostPacketPlayReceiveEvent;
import io.github.retrooper.packetevents.packettype.PacketType;
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
public class BadPacketsB extends Check {
    private boolean diggingSent = false;
    private int buffer = 0;

    public BadPacketsB(User user) {
        super(user, "BadPackets (B)", Arrays.asList("Detecta KillAura AutoBlock"));
    }

    @Override
    public void onPostPacketPlayReceive(PostPacketPlayReceiveEvent event) {
        if (event.getPacketId() == PacketType.Play.Client.USE_ENTITY) {
            WrappedPacketInUseEntity wrapper = new WrappedPacketInUseEntity(event.getNMSPacket());

            if (wrapper.getAction() == WrappedPacketInUseEntity.EntityUseAction.ATTACK) {
                if (getUser().getPlayer().getItemInHand() != null && getUser().getPlayer().getItemInHand().getType().toString().contains("SWORD")) {
                    if (diggingSent) {
                        if (buffer++ > 5) {
                            fail("b=true buffer="+buffer);
                        }
                    } else {
                        buffer = 0;
                    }
                }

            }
        }else if (event.getPacketId() == PacketType.Play.Client.BLOCK_DIG) {
            diggingSent = true;
        } else if (event.getPacketId() == PacketType.Play.Client.FLYING) {
            diggingSent = false;
        }
    }
}
