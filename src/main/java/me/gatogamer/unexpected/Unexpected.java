package me.gatogamer.unexpected;

import com.google.common.util.concurrent.MoreExecutors;
import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.event.manager.PEEventManager;
import io.github.retrooper.packetevents.settings.PacketEventsSettings;
import io.github.retrooper.packetevents.utils.server.ServerVersion;
import lombok.Getter;

import me.gatogamer.unexpected.listener.ConnectionListener;
import me.gatogamer.unexpected.packet.NyacketListener;
import me.gatogamer.unexpected.task.ServerTickTask;
import me.gatogamer.unexpected.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public final class Unexpected extends JavaPlugin {

    @Getter
    private static Unexpected instance;

    private PacketEvents packetEvents;

    private ExecutorService executorService;

    private UserManager userManager;

    private ServerTickTask serverTickTask;

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        log("&7Loading &cUnexpected&7...");

        log("&7Firing up the &cthread turbines&7!");
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
        log("&cThread turbines &7have been fired!");

        packetEvents = PacketEvents.create(this);
        PacketEventsSettings settings = packetEvents.getSettings();

        settings.fallbackServerVersion(ServerVersion.v_1_8_8)
                .compatInjector(true)
                .bStats(false);

        packetEvents.loadAsync(executorService);
    }

    @Override
    public void onEnable() {
        serverTickTask = new ServerTickTask();
        serverTickTask.runTaskTimer(this, 0L, 1L);

        packetEvents.init();

        userManager = new UserManager();

        NyacketListener nyacketListener = new NyacketListener(this);

        packetEvents.getEventManager().registerListener(nyacketListener);
        Bukkit.getPluginManager().registerEvents(nyacketListener, this);

        Bukkit.getPluginManager().registerEvents(new ConnectionListener(), this);

        Bukkit.getOnlinePlayers().forEach(userManager::getUserOrCreate);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(userManager::deleteUser);
        packetEvents.terminate();
    }

    public void log(String s) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUnexpected &8> &7" + s));
    }

}
