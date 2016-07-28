package co.insou.gui;

import co.insou.gui.page.GUIPage;
import co.insou.gui.page.InternalType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A wrapper used for each online player internally by gui
 */
public class GUIPlayer {

    private final UUID uuid;
    private final JavaPlugin plugin;

    private final List<GUIPage> crumb = new ArrayList<>();

    private final List<Class<? extends GUIPage>> ignores = new ArrayList<>();
    private final List<InternalType> internalIgnores = new ArrayList<>();

    private final List<Class<? extends GUIPage>> cancels = new ArrayList<>();
    private final List<InternalType> internalCancels = new ArrayList<>();

    /**
     * GUIPlayer constructor (will be invoked automagically by GUIManager
     * @param plugin    the plugin gui is registered to
     * @param player    the player this class is wrapping
     */
    public GUIPlayer(JavaPlugin plugin, Player player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
    }

    /**
     * Returns whether the player is currently in an inventory being controlled by gui
     *
     * @return  whether the player is in a managed gui
     */
    public boolean inGUI() {
        return crumb.size() > 0;
    }

    /**
     * Opens the given page to the player, and whether or not to document the opening
     *
     * <p>
     * If document is set to true, it will be added to the player's "breadcrumb" or "stack", effectively
     * meaning if another inventory is opened on top and then closed, it will reopen this page
     * </p>
     *
     * @param page      The page to open
     * @param document  Whether or not to document the page
     */
    public void openPage(GUIPage page, boolean document) {
        if (document) {
            crumb.add(page);
        }
        internalIgnores.add(InternalType.PLAYER);
        page.open();
        internalIgnores.remove(InternalType.PLAYER);
    }

    /**
     * Returns the page the player is currently on, or null if none exists
     *
     * @return  The player's current page, or null
     */
    public GUIPage currentPage() {
        if (!inGUI()) {
            return null;
        }
        return crumb.get(currentIndex());
    }

    private int currentIndex() {
        return crumb.size() - 1;
    }

    /**
     * Called when the player's page is closed by
     * @see co.insou.gui.page.GUIPage
     */
    public void onClose() {
        if (inGUI()) {
            crumb.remove(currentIndex());
        }
    }

    void onInventoryClick(InventoryClickEvent event) {
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

    void onInventoryClose(InventoryCloseEvent event) {
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

    /**
     * Closes the player's current page if they are currently in gui
     */
    public void closePage() {
        if (inGUI()) {
            currentPage().onClose();
        }
    }

    /**
     * Clears the player's complete gui stack, if closeInventory is set to true, it will also physically close the player's inventory
     *
     * @param closeInventory    Whether or not to close the player's physically open inventory
     */
    public void closeGUI(boolean closeInventory) {
        crumb.clear();
        if (closeInventory) {
            player().closeInventory();
        }
    }

    /**
     * Returns whether or not gui is currently ignoring inventory closes
     *
     * @return whether or not closes are being ignored
     */
    public boolean ignoring() {
        return internalIgnores.size() > 0 || ignores.size() > 0;
    }

    /**
     * Start ignoring inventory closes, registering to the "ignore" parameter's class
     *
     * @param ignore    The page type to register to
     */
    public void ignore(GUIPage ignore) {
        ignore(ignore.getClass());
    }

    /**
     * Start ignoring inventory closes, registering to the "ignore" parameter class
     *
     * @param ignore    The page type to register to
     */
    public void ignore(Class<? extends GUIPage> ignore) {
        ignores.add(ignore);
    }

    /**
     * Stop ignoring inventory closes registered to the "ignore" parameter's class
     *
     * @param ignore    The page type to unregister from
     */
    public void stopIgnore(GUIPage ignore) {
        stopIgnore(ignore.getClass());
    }

    /**
     * Stop ignoring inventory closes registered to the "ignore" parameter class
     *
     * @param ignore    The page type to unregister from
     */
    public void stopIgnore(Class<? extends GUIPage> ignore) {
        ignores.remove(ignore);
    }

    /**
     * Internal ignores, called by
     * @see co.insou.gui.page.PageInventory
     *
     * @param type  The type to register the ignorance to
     */
    public void internalIgnore(InternalType type) {
        internalIgnores.add(type);
    }

    /**
     * Stop internal ignores, called by
     * @see co.insou.gui.page.PageInventory
     *
     * @param type  The type to unregister the ignorance from
     */
    public void stopInternalIgnore(InternalType type) {
        internalIgnores.remove(type);
    }

    /**
     * Returns whether or not gui is currently cancelling inventory closes
     *
     * @return whether or not closes are being cancelled
     */
    public boolean cancelling() {
        return internalCancels.size() > 0 || cancels.size() > 0;
    }

    /**
     * Start cancelling inventory closes, registering to the "cancel" parameter's class
     *
     * @param cancel    The page type to register to
     */
    public void cancel(GUIPage cancel) {
        cancel(cancel.getClass());
    }

    /**
     * Start cancelling inventory closes, registering to the "cancel" parameter class
     *
     * @param cancel    The page type to register to
     */
    public void cancel(Class<? extends GUIPage> cancel) {
        cancels.add(cancel);
    }

    /**
     * Stop cancelling inventory closes registered to the "cancel" parameter's class
     *
     * @param cancel    The page type to unregister from
     */
    public void stopCancel(GUIPage cancel) {
        stopCancel(cancel.getClass());
    }

    /**
     * Stop cancelling inventory closes registered to the "cancel" parameter class
     *
     * @param cancel    The page type to unregister from
     */
    public void stopCancel(Class<? extends GUIPage> cancel) {
        cancels.remove(cancel);
    }

    /**
     * Internal cancelling
     *
     * @param type  The type to register the cancel to
     */
    public void internalCancel(InternalType type) {
        internalCancels.add(type);
    }

    /**
     * Stop internal cancelling
     *
     * @param type  The type to register the cancel to
     */
    public void stopInternalCancel(InternalType type) {
        internalCancels.remove(type);
    }

    /**
     * Returns the player this wrapper is registered to
     *
     * @return  The Player this class is registered to
     */
    public Player player() {
        return plugin.getServer().getPlayer(uuid);
    }


}
