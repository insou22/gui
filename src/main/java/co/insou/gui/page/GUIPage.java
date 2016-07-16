package co.insou.gui.page;

import co.insou.gui.GUIPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class GUIPage<P extends JavaPlugin> {

    protected final P plugin;
    protected final GUIPlayer player;
    protected final String title;

    private GUIInventory inventory;

    public GUIPage(P plugin, GUIPlayer player, String title) {
        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.inventory = loadInventory();
    }

    protected abstract GUIInventory loadInventory();

    public final void open() {
        if (inventory == null) {
            throw new IllegalStateException("Inventory has not been initialized");
        }
        inventory.open();
        onInventoryOpen();
    }

    protected void onInventoryOpen() {}

    public final void onClose() {
        player.onClose();
        if (player.inGUI()) {
            player.openPage(player.currentPage(), false);
        }
        onInventoryClose();
    }

    protected void onInventoryClose() {}

    public final void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (inventory.hasAction(event.getSlot())) {
            inventory.executeAction(event.getSlot(), event);
        } else {
            onInventoryClick(event);
        }
    }

    protected void onInventoryClick(InventoryClickEvent event) {}

    public final String getTitle() {
        return title;
    }


}
