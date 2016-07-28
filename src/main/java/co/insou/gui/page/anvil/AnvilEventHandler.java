package co.insou.gui.page.anvil;

/**
 * An AnvilEventHandler is an overloadable interface used to listen for a type of AnvilEvent
 * @param <T>   The type of AnvilEvent to accept
 */
public interface AnvilEventHandler<T extends AnvilEvent> {

    /**
     * Accept the associated AnvilEvent
     *
     * @param event The event that was called
     */
    void accept(T event);

}
