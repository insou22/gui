package co.insou.gui.page.anvil;

/**
 * Thrown when the gui framework does not support the current version of CraftBukkit
 */
public class VersionUnsupportedException extends RuntimeException {

    public VersionUnsupportedException() {
        super();
    }

    public VersionUnsupportedException(String message) {
        super(message);
    }

}
