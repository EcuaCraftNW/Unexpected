package me.gatogamer.unexpected.user;

import lombok.Getter;
import me.gatogamer.unexpected.check.Check;
import me.gatogamer.unexpected.check.impl.player.combat.AimAssistA;
import me.gatogamer.unexpected.check.impl.player.combat.HitBoxA;
import me.gatogamer.unexpected.check.impl.player.packets.BadPacketsA;
import me.gatogamer.unexpected.check.impl.player.packets.BadPacketsB;
import me.gatogamer.unexpected.check.impl.player.packets.NoPitchLimit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class User {

    private final UUID uuid;

    private boolean alerts = true;

    private final List<Check> checks = new ArrayList<>();

    public User(UUID uuid) {
        this.uuid = uuid;

        checks.add(new BadPacketsA(this));
        checks.add(new BadPacketsB(this));
        checks.add(new NoPitchLimit(this));
        checks.add(new AimAssistA(this));
        checks.add(new HitBoxA(this));
    }

    public void alert(String formatted) {
        if (canAlerts()) {
            getPlayer().sendMessage(formatted);
        }
    }

    public boolean canAlerts() {
        return alerts && getPlayer().hasPermission("unexpected.alerts");
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

}