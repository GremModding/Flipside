package io.siuolplex.untitledlib.util.fabric;

import io.siuolplex.untitledlib.util.multiloader.LoaderWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FabricLoaderWrapper implements LoaderWrapper {
    @Override
    public boolean isDevMode() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public String getLoader() {
        return "fabric";
    }

    @Override
    public Path getPath(String string) {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public boolean isModPresent(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

}
