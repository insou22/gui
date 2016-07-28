package co.insou.gui.page.anvil;

import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Event called when an anvil is closed
 */
public class AnvilCloseEvent extends AnvilEvent {

    private InventoryCloseEvent event;
    private boolean planned;

    /**
     * AnvilCloseEvent constructor
     *
     * @param event     The associated InventoryCloseEvent
     * @param planned   Whether or not the closing was planned or not
     */
    public AnvilCloseEvent(InventoryCloseEvent event, boolean planned)
    {
        this.event = event;
        this.planned = planned;
    }

    /**
     * Returns the associated InventoryCloseEvent
     *
     * @return the associated InventoryCloseEvent
     */
    public InventoryCloseEvent getEvent()
    {
        return event;
    }

    /**
     * Returns whether the closing was planned or not
     *
     * @return whether the closing was planned or not
     */
    public boolean isPlanned()
    {
        return planned;
    }
}
