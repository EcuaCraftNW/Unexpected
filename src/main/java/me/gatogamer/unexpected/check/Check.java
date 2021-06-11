package me.gatogamer.unexpected.check;

import io.github.retrooper.packetevents.event.PacketListenerAbstract;
import lombok.Getter;
import me.gatogamer.unexpected.Unexpected;
import me.gatogamer.unexpected.event.ServerTickEvent;
import me.gatogamer.unexpected.user.User;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public abstract class Check extends PacketListenerAbstract {

    private final User user;
    private final String name;
    private final String displayName;
    private final boolean ban;
    private final boolean notify;
    private final int maxVl;
    private int vl = 0;
    private final List<String> description;

    public Check(User user, String name, List<String> description) {
        this.user = user;
        this.name = name;
        displayName = Unexpected.getInstance().getConfig().getString("checks." + name + ".displayName", name);
        ban = Unexpected.getInstance().getConfig().getBoolean("checks." + name + ".ban", true);
        notify = Unexpected.getInstance().getConfig().getBoolean("checks." + name + ".notify", true);
        maxVl = Unexpected.getInstance().getConfig().getInt("checks." + name + ".maxVl", 10);
        this.description = description;
    }

    protected void fail(String data) {
        vl++;
        String formatted = ChatColor.translateAlternateColorCodes('&',
                Unexpected.getInstance().getConfig().getString("alerts.message")
                        .replaceAll("%vl%", String.valueOf(vl))
                        .replaceAll("%check%", String.valueOf(this.getDisplayName()))
                        .replaceAll("%player%", String.valueOf(user.getPlayer().getName()))
                        .replaceAll("%data%", data)
        );

        Unexpected.getInstance().getUserManager().getUsers().forEach(((uuid, user) -> user.alert(formatted)));
    }

    public void onMove(PlayerMoveEvent event) {}

    public void onTick(ServerTickEvent event) {}

}