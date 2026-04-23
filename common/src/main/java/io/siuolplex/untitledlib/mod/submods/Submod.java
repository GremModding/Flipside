package io.siuolplex.untitledlib.mod.submods;

/**
 * Serves the purpose of providing an initialization class for any submods. Mainly for SIE really.
 */
public interface Submod {
    /**
     * Equivalent to Fabric's ModInitializer or Neo's mod constructor
     */
    void startMod();

    String getSubmodID();

}
