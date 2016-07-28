package co.insou.gui.page;

import co.insou.gui.GUIPlayer;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A GUIPage class. You should create a new class extending GUIPage for each unique page you plan to
 * include in your plugin.
 *
 * <p>
 * To allow direct automatic access to your Main class instance, include the Main class name in your GUIPage definition,
 * for example: class FooPage extends GUIPage<MainPlugin> {
 * </p>
 *
 * @param <P>   Your Main (Java)Plugin class
 */
public abstract class GUIPage<P extends JavaPlugin> {

    protected final P plugin;
    protected final GUIPlayer player;
    protected final String title;
    protected final Object[] params;

    private GUIInventory inventory;

    /**
     * GUIPage super constructor to be invoked by your GUIPage sub-class definitions
     *
     * <p>
     * **IMPORTANT**
     * If rely on other external state than your plugin/player can provide in your loadInventory method, set them in the
     * params Object[] definition and then blind cast in your loadInventory method from the protected params field
     * </p>
     *
     * @param plugin    Your Main plugin instance
     * @param player    The player the page is being opened for
     * @param title     The title of the inventory
     * @param params    Parameters to be used in your loadInventory method
     */
    public GUIPage(P plugin, GUIPlayer player, String title, Object... params) {
        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.params = params;
        this.inventory = loadInventory();
    }

    /**
     * Returns the completely loaded GUIInventory implementation to be opened for the player
     *
     * <p>
     * Remember to use the params field if you rely on other external state than your plugin/player can provide, as setting
     * them to fields in your own definition will not work as loadInventory is called in the bottom of the super-constructor
     * </p>
     *
     * @return The compltely loaded inventory
     */
    protected abstract GUIInventory loadInventory();

    /**
     * Open the inventory for the player
     */
    public final void open() {
        if (inventory == null) {
            throw new IllegalStateException("Inventory has not been initialized");
        }
        inventory.open();
        onInventoryOpen();
    }

    /**
     * Overloadable method, called when the inventory is opened
     */
    protected void onInventoryOpen() {}

    /**
     * Called when the page is closed by
     * @see GUIPlayer
     */
    public final void onClose() {
        player.onClose();
        if (player.inGUI()) {
            player.openPage(player.currentPage(), false);
        }
        onInventoryClose();
    }

    /**
     * Overloadable method, called when the inventory is closed
     */
    protected void onInventoryClose() {}

    /**
     * Called when the player clicks in the page by
     * @see GUIPlayer
     *
     * @param event The associated InventoryClickEvent
     */
    public final void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if (inventory.hasAction(event.getSlot())) {
            inventory.executeAction(event.getSlot(), event);
        } else {
            if (event.getInventory().getItem(event.getSlot()) != null) {
                onInventoryClick(event);
            }
        }
    }

    /**
     * Overloadable method, called when the player clicks in the page
     *
     * @param event The associated InventoryClickEvent
     */
    protected void onInventoryClick(InventoryClickEvent event) {}

    /**
     * Returns the inventory's title
     *
     * @return  The inventory's title
     */
    public final String getTitle() {
        return title;
    }


}
