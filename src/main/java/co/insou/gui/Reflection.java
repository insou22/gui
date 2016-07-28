package co.insou.gui;

import org.bukkit.Bukkit;

/**
 * Reflection used internally by gui
 */
public class Reflection {

    private static Reflection reflection;

    /**
     * Returns the Reflection singleton instance
     *
     * @return the Reflection singleton instance
     */
    public static Reflection getInstance() {
        if (reflection == null) {
            reflection = new Reflection();
        }
        return reflection;
    }

    private String version;

    private Reflection()
    {
        final String name = Bukkit.getServer().getClass().getPackage().getName();
        this.version = name.substring(name.lastIndexOf('.') + 1);
    }

    /**
     * Returns the server's current version, for example: v1_8_R3
     *
     * @return the server's current CraftBukkit version and revision mapping
     */
    public String getVersion()
    {
        return version;
    }

}
