package co.insou.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A manager for the gui framework
 *
 * <p>
 * A new GUIManager must be invoked for each plugin using this framework
 * </p>
 */
public class GUIManager {

    private final JavaPlugin plugin;
    private final Map<UUID, GUIPlayer> players = new HashMap<>();

    /**
     * Register a new GUIManager for your plugin
     *
     * <p>
     * A new GUIManager must be invoked for each plugin using this framework
     * </p>
     *
     * @param plugin    Your plugin's main class to register to
     */
    public GUIManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new GUIListener(), plugin);

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            register(player);
        }
    }

    private GUIPlayer register(Player player) {
        return players.put(player.getUniqueId(), new GUIPlayer(plugin, player));
    }

    private GUIPlayer deregister(Player player) {
        return players.remove(player.getUniqueId());
    }

    public GUIPlayer getPlayer(Player player) {
        GUIPlayer guiPlayer = players.get(player.getUniqueId());
        if (guiPlayer == null) {
            guiPlayer = register(player);
        }
        return guiPlayer;
    }

    private class GUIListener implements Listener {

        @EventHandler
        public void on(PlayerJoinEvent event) {
            register(event.getPlayer());
        }

        @EventHandler
        public void on(PlayerQuitEvent event) {
            deregister(event.getPlayer());
        }

        @EventHandler
        public void on(InventoryClickEvent event) {
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }
            GUIPlayer player = getPlayer((Player) event.getWhoClicked());
            if (player.inGUI()) {
                player.onInventoryClick(event);
            }
        }

        @EventHandler
        public void on(InventoryCloseEvent event) {
            if (!(event.getPlayer() instanceof Player)) {
                return;
            }
            GUIPlayer player = getPlayer((Player) event.getPlayer());
            if (player.inGUI()) {
                player.onInventoryClose(event);
            }
        }

    }

}
