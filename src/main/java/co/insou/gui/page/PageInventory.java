package co.insou.gui.page;

import co.insou.colorchar.ColorChar;
import co.insou.gui.GUIPlayer;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The PageInventory class represents an inventory of dynamic size, automatically expanding to fit more items
 */
public class PageInventory implements GUIInventory {

    private static final ItemStack BACK_BUTTON;
    private static final ItemStack FORWARD_BUTTON;

    static {
        BACK_BUTTON = new ItemStack(Material.ARROW);
        FORWARD_BUTTON = new ItemStack(Material.ARROW);

        ItemMeta backMeta = BACK_BUTTON.getItemMeta();
        backMeta.setDisplayName(ColorChar.color("&bBack Page"));
        BACK_BUTTON.setItemMeta(backMeta);

        ItemMeta forwardMeta = FORWARD_BUTTON.getItemMeta();
        forwardMeta.setDisplayName(ColorChar.color("&bForward Page"));
        FORWARD_BUTTON.setItemMeta(forwardMeta);
    }

    private final GUIPlayer player;

    private final List<ItemStack> contents = Lists.newArrayList();
    private final String title;

    private int page = 1;
    private int totalPages = 1;

    /**
     * PageInventory constructor
     *
     * @param player    The player to open the inventory to
     * @param title     The title of the inventory
     */
    public PageInventory(GUIPlayer player, String title) {
        this.player = player;
        this.title = title;
    }

    /**
     * Add an item to the inventory
     *
     * @param item  The item to add
     * @return      The current PageInventory instance
     */
    public PageInventory addItem(ItemStack item) {
        this.contents.add(item);
        this.recalculate();
        return this;
    }

    /**
     * Adds items to the inventory
     *
     * @param items  The items to add
     * @return The current PageInventory instance
     */
    public PageInventory withItems(Collection<ItemStack> items) {
        this.contents.addAll(items);
        this.recalculate();
        return this;
    }

    /**
     * Adds items to the inventory
     *
     * @param items  The items to add
     * @return      The current PageInventory instance
     */
    public PageInventory withItems(ItemStack... items) {
        this.contents.addAll(Arrays.asList(items));
        this.recalculate();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() {
        if (totalPages == 1) {
            Inventory inventory = Bukkit.createInventory(null, calcSize(contents.size()), title);
            int slot = 0;
            for (ItemStack item : contents)
            {
                inventory.setItem(slot++, item);
            }
            player.internalIgnore(InternalType.PAGES);
            player.player().openInventory(inventory);
            player.stopInternalIgnore(InternalType.PAGES);
            return;
        }

        int startPoint = (this.page - 1) * 45;
        List<ItemStack> invContents = Lists.newArrayList();

        ItemStack item;
        try {
            while ((item = this.contents.get(startPoint++)) != null) {
                invContents.add(item);
                if (startPoint - ((this.page - 1) * 45) == 45) break;
            }
        }
        catch (IndexOutOfBoundsException ignored) {}

        Inventory inventory = Bukkit.createInventory(null, 54, this.title);

        int slot = 0;
        for (ItemStack invItem : invContents) {
            inventory.setItem(slot++, invItem);
        }

        if (this.page > 1) {
            inventory.setItem(45, PageInventory.BACK_BUTTON);
        }
        if (this.page < this.getPages(this.contents.size())) {
            inventory.setItem(53, PageInventory.FORWARD_BUTTON);
        }

        player.internalIgnore(InternalType.PAGES);
        player.player().openInventory(inventory);
        player.stopInternalIgnore(InternalType.PAGES);
    }

    private void backPage()
    {
        page--;
        open();
    }

    private void forwardPage()
    {
        page++;
        open();
    }

    private void recalculate() {
        this.totalPages = this.contents.size() > 54 ? this.contents.size() / 45 : 1;
    }

    private int calcSize(int size) {
        return (((size - 1) / 9) + 1) * 9;
    }

    private int getPages(int size) {
        if (size % 45 == 0) {
            return size / 45;
        }
        Double d = ((double) size + 1) / 45;
        return (int) Math.ceil(d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAction(int slot) {
        if (totalPages > 1) {
            return (slot == 45) || (slot == 53);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeAction(int slot, InventoryClickEvent event) {
        if (slot == 45 && page > 1) {
            backPage();
        }
        if (slot == 53 && this.page < this.getPages(this.contents.size())) {
            forwardPage();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(int slot, ItemStack item) {
        throw new RuntimeException("Setting items not supported in PageInventory");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItem(int slot, ItemStack item, InventoryAction action) {
        throw new RuntimeException("Setting items not supported in PageInventory");
    }

}
