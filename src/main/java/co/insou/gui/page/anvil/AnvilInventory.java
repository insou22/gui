package co.insou.gui.page.anvil;

import co.insou.gui.GUIPlayer;
import co.insou.gui.Reflection;
import co.insou.gui.page.GUIInventory;
import co.insou.gui.page.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AnvilInventory implements GUIInventory {

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

    public abstract AnvilInventory setItem(AnvilSlot slot, ItemStack item);

    public abstract AnvilInventory setItem(AnvilSlot slot, ItemStack item, InventoryAction action);

    public abstract AnvilInventory withClickConsumer(AnvilEventHandler<AnvilClickEvent> handler);

    public abstract AnvilInventory withCloseConsumer(AnvilEventHandler<AnvilCloseEvent> handler);

}
