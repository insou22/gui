package co.insou.gui.page.anvil;

import co.insou.gui.GUIPlayer;
import co.insou.gui.Reflection;
import co.insou.gui.page.GUIInventory;
import co.insou.gui.page.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The AnvilInventory class represents an inventory in an anvil, normally used to get text input from a player
 */
public abstract class AnvilInventory implements GUIInventory {

    /**
     * Get the current server version's associated AnvilInventory
     *
     * @param plugin    The registered plugin's instance
     * @param player    The player to open the inventory to
     * @return A new AnvilInventory of the current server version's type
     * @throws VersionUnsupportedException if the server's version is not supported
     */
    public static AnvilInventory getInventory(JavaPlugin plugin, GUIPlayer player) {
        switch (Reflection.getInstance().getVersion()) {
            case "v1_8_R1":
                return new Anvil_v1_8_R1(plugin, player);
            case "v1_8_R2":
                return new Anvil_v1_8_R2(plugin, player);
            case "v1_8_R3":
                return new Anvil_v1_8_R3(plugin, player);
            case "v1_9_R1":
                return new Anvil_v1_9_R1(plugin, player);
            case "v1_9_R2":
                return new Anvil_v1_9_R2(plugin, player);
            case "v1_10_R1":
                return new Anvil_v1_10_R1(plugin, player);
        }
        throw new VersionUnsupportedException("Your version was not found! Are you running before 1.8 or after 1.10.2?");
    }

    /**
     * Sets an item in an AnvilSlot
     *
     * @param slot  The slot to put the item in
     * @param item  The item
     * @return The AnvilInventory instance
     */
    public abstract AnvilInventory setItem(AnvilSlot slot, ItemStack item);

    /**
     * Sets an item in the AnvilSlot, with an InventoryAction to listen for players clicking the item
     *
     * @param slot      The slot to put the item in
     * @param item      The item
     * @param action    The Action to be called when the item is clicked
     * @return The AnvilInventory instance
     */
    public abstract AnvilInventory setItem(AnvilSlot slot, ItemStack item, InventoryAction action);

    /**
     * Adds a consumer to listen for an AnvilClickEvent
     *
     * @param handler   The AnvilEventHandler to listen for the click
     * @return  The AnvilInventory instance
     */
    public abstract AnvilInventory withClickConsumer(AnvilEventHandler<AnvilClickEvent> handler);

    /**
     * Adds a consumer to listen for an AnvilCloseEvent
     *
     * @param handler   The AnvilEventHandler to listen for the close
     * @return  The AnvilInventory instance
     */
    public abstract AnvilInventory withCloseConsumer(AnvilEventHandler<AnvilCloseEvent> handler);

}
