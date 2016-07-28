package co.insou.gui.page;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Used to listen for clicks on a specific slot in an inventory
 */
public interface InventoryAction {

    /**
     * Called when the slot is clicked
     *
     * @param event The associated InventoryClickEvent
     */
    void execute(InventoryClickEvent event);

}
