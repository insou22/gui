package co.insou.gui;

import co.insou.gui.page.GUIPage;
import co.insou.gui.page.InternalType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GUIPlayer {

    private final UUID uuid;
    private final JavaPlugin plugin;

    private final List<GUIPage> crumb = new ArrayList<>();

    private final List<Class<? extends GUIPage>> ignores = new ArrayList<>();
    private final List<InternalType> internalIgnores = new ArrayList<>();

    private final List<Class<? extends GUIPage>> cancels = new ArrayList<>();
    private final List<InternalType> internalCancels = new ArrayList<>();

    public GUIPlayer(JavaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
    }

    public boolean inGUI() {
        return crumb.size() > 0;
    }

    public void openPage(GUIPage page, boolean doc) {
        if (doc) {
            crumb.add(page);
        }
        internalIgnores.add(InternalType.PLAYER);
        page.open();
        internalIgnores.remove(InternalType.PLAYER);
    }

    public GUIPage currentPage() {
        if (!inGUI()) {
            return null;
        }
        return crumb.get(currentIndex());
    }

    public int currentIndex() {
        return crumb.size() - 1;
    }

    public void onClose() {
        if (inGUI()) {
            crumb.remove(currentIndex());
        }
    }

    public void onInventoryClick(InventoryClickEvent event) {
        if (!inGUI()) {
            return;
        }
        if (event.getClickedInventory() == null) {
            return;
        }
        if (!event.getClickedInventory().equals(player().getOpenInventory().getTopInventory())) {
            return;
        }
        currentPage().onClick(event);
    }

    public void onInventoryClose(InventoryCloseEvent event) {
        if (ignoring()) {
            return;
        }
        if (cancelling()) {
            openPage(currentPage(), false);
        }
        if (inGUI()) {
            plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    currentPage().onClose();
                }
            });
        }
    }

    public void closePage() {
        currentPage().onClose();
    }

    public void closeGUI(boolean closeInventory) {
        crumb.clear();
        if (closeInventory) {
            player().closeInventory();
        }
    }

    public boolean ignoring() {
        return internalIgnores.size() > 0 || ignores.size() > 0;
    }

    public void ignore(GUIPage ignore) {
        ignore(ignore.getClass());
    }

    public void ignore(Class<? extends GUIPage> ignore) {
        ignores.add(ignore);
    }

    public void stopIgnore(GUIPage ignore) {
        stopIgnore(ignore.getClass());
    }

    public void stopIgnore(Class<? extends GUIPage> ignore) {
        ignores.remove(ignore);
    }

    public void internalIgnore(InternalType type) {
        internalIgnores.add(type);
    }

    public void stopInternalIgnore(InternalType type) {
        internalIgnores.remove(type);
    }

    public boolean cancelling() {
        return internalCancels.size() > 0 || cancels.size() > 0;
    }

    public void cancel(GUIPage ignore) {
        cancel(ignore.getClass());
    }

    public void cancel(Class<? extends GUIPage> ignore) {
        cancels.add(ignore);
    }

    public void stopCancel(GUIPage ignore) {
        stopCancel(ignore.getClass());
    }

    public void stopCancel(Class<? extends GUIPage> ignore) {
        cancels.remove(ignore);
    }

    public void internalCancel(InternalType type) {
        internalCancels.add(type);
    }

    public void stopInternalCancel(InternalType type) {
        internalCancels.remove(type);
    }

    public Player player() {
        return plugin.getServer().getPlayer(uuid);
    }


}
