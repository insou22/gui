package co.insou.gui.page.anvil;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Event called when an anvil is clicked
 */
public class AnvilClickEvent extends AnvilEvent {

    private AnvilSlot slot;
    private String name;
    private InventoryClickEvent event;

    private boolean close = true;
    private boolean destroy = true;

    /**
     * AnvilClickEvent constructor
     *
     * @param slot  The slot that was clicked
     * @param name  The current text input in the anvil
     * @param event The associated InventoryClickEvent
     */
    public AnvilClickEvent(AnvilSlot slot, String name, InventoryClickEvent event)
    {
        this.slot = slot;
        this.name = name;
        this.event = event;
    }

    /**
     * Returns the slot that was clicked
     *
     * @return the slot that was clicked
     */
    public AnvilSlot getSlot()
    {
        return slot;
    }

    /**
     * Returns the current text input the anvil
     *
     * @return the current text input the anvil
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the associated InventoryClickEvent
     *
     * @return the associated InventoryClickEvent
     */
    public InventoryClickEvent getEvent()
    {
        return event;
    }

    /**
     * Returns whether or not the inventory will close itself
     *
     * @return whether or not the inventory will close itself
     */
    public boolean isWillClose()
    {
        return close;
    }

    /**
     * Sets whether or not the inventory will close itself
     *
     * @param close whether or not the inventory will close itself
     */
    public void setWillClose(boolean close)
    {
        this.close = close;
    }
}
