package co.insou.gui.page;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The generic inventory interface implemented by the different inventory types in the gui framework
 *
 * @see co.insou.gui.page.StandardInventory
 * @see co.insou.gui.page.PageInventory
 * @see co.insou.gui.page.anvil.AnvilInventory
 */
public interface GUIInventory {

    /**
     * Open the inventory
     */
    void open();

    /**
     * Returns whether or not the selected slot has an action
     *
     * @param slot  The slot clicked on
     * @return  Whether or not the slot has an InventoryAction associated to it
     */
    boolean hasAction(int slot);

    /**
     * Executes the action associated to the given slot
     *
     * @param slot  The slot clicked on
     * @param event The relative InventoryClickEvent
     */
    void executeAction(int slot, InventoryClickEvent event);

    /**
     * Sets an ItemStack in the inventory's given slot
     *
     * @param slot  The slot to put the item in
     * @param item  The item to set
     */
    void setItem(int slot, ItemStack item);

    /**
     * Sets an ItemStack in the inventory's given slot, with an InventoryAction to listen for players clicking the item
     *
     * @param slot      The slot to put the item in
     * @param item      The item to set
     * @param action    The action to be called when the item is clicked
     */
    void setItem(int slot, ItemStack item, InventoryAction action);

}
