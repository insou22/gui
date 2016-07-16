package co.insou.gui.page.anvil;

public interface AnvilEventHandler<T extends AnvilEvent> {

    void accept(T event);

}
