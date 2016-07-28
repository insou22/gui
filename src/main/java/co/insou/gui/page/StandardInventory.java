package co.insou.gui.page;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * The StandardInventory class represents a statically sized GUIInventory of a "Standard" nature
 */
public class StandardInventory implements GUIInventory {

    private final Player player;
    private final Map<Integer, InventoryAction> actions = new HashMap<>();

    private final Inventory inventory;

    /**
     * StandardInventory constructor
     *
     * @param player    The player to open the inventory to
     * @param size      The size of the inventory (must be multiple of 9)
     * @param title     The title of the inventory
     */
    public StandardInventory(Player player, int size, String title) {
        this.player = player;
        this.inventory = Bukkit.createInventory(null, size, title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        player.openInventory(inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAction(int slot) {
        return actions.containsKey(slot);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeAction(int slot, InventoryClickEvent event) {
        if (!hasAction(slot)) {
            return;
        }
        actions.get(slot).execute(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(int slot, ItemStack item, InventoryAction action) {
        setItem(slot, item);
        actions.put(slot, action);
    }

}
