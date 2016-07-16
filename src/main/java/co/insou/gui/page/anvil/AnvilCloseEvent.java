package co.insou.gui.page.anvil;

import org.bukkit.event.inventory.InventoryCloseEvent;

public class AnvilCloseEvent extends AnvilEvent {

    private InventoryCloseEvent event;
    private boolean planned;

    public AnvilCloseEvent(InventoryCloseEvent event, boolean planned)
    {
        this.event = event;
        this.planned = planned;
    }

    public InventoryCloseEvent getEvent()
    {
        return event;
    }

    public boolean isPlanned()
    {
        return planned;
    }
}
