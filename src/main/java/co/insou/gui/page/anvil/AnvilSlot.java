package co.insou.gui.page.anvil;

public enum AnvilSlot {

    INPUT_LEFT(0),
    INPUT_RIGHT(1),
    OUTPUT(2);

    private int slot;

    AnvilSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Returns the int value of the AnvilSlot
     *
     * @return the int value of the slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Get the associated AnvilSlot from int slot
     *
     * @param slot  The int value of the AnvilSlot
     * @return The associated AnvilSlot
     */
    public static AnvilSlot bySlot(int slot) {
        for (AnvilSlot anvilSlot : values()) {
            if (anvilSlot.getSlot() == slot) {
                return anvilSlot;
            }
        }

        return null;
    }

}
