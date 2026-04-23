package io.siuolplex.untitledlib.builder;

import net.minecraft.resources.ResourceKey;

public interface BIRObjBuilder<T> {
    boolean isBuilderFrozen();

    T addToRegistry();

    ResourceKey<T> getKey();

    default void throwIfFrozenBuilder() {
        if (isBuilderFrozen()) {
            throw new BIRObjBuilder.ChangeWhileFrozenException("Changed variables for " + getKey().location() + " in registry " + getKey().registry());
        }
    }

    class ChangeWhileFrozenException extends RuntimeException {
        public ChangeWhileFrozenException(String message) {
            super(message);
        }
    }
}
