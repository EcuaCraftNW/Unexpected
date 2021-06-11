package me.gatogamer.unexpected.user;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class UserManager {

    @Getter
    private final ConcurrentHashMap<UUID, User> users = new ConcurrentHashMap<>();

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public User getUserOrCreate(UUID uuid) {
        return users.computeIfAbsent(uuid, User::new);
    }

    public User getUserOrCreate(Player player) {
        return getUserOrCreate(player.getUniqueId());
    }

    public void deleteUser(Player player) {
        deleteUser(player.getUniqueId());
    }

    public void deleteUser(UUID uuid) {
        users.remove(uuid);
    }

}