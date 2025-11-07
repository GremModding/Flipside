package io.siuolplex.untitledlib.util.multiloader;

import java.nio.file.Path;

/**
 * A set of small util stuff for Loaders.
 * <br> Might need to be expanded over time, who knows.
 *
 */
public interface LoaderWrapper {
    default boolean isDevMode() {
        throw new NoLoaderProvidedException();
    }

    default boolean isClient() {
        throw new NoLoaderProvidedException();
    }

    default String getLoader() {
        throw new NoLoaderProvidedException();
    }

    default Path getPath(String string) {
        throw new NoLoaderProvidedException();
    }

    default boolean isModPresent(String mod) {
        throw new NoLoaderProvidedException();
    }



    class NoLoaderProvidedException extends RuntimeException {
    }
}
