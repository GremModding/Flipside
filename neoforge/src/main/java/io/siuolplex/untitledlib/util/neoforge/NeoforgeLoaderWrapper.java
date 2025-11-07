package io.siuolplex.untitledlib.util.neoforge;

import io.siuolplex.untitledlib.util.multiloader.LoaderWrapper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

import java.nio.file.Path;

public class NeoforgeLoaderWrapper implements LoaderWrapper {
    @Override
    public boolean isDevMode() {
        return !FMLLoader.getCurrent().isProduction();
    }

    @Override
    public boolean isClient() {
        return FMLLoader.getCurrent().getDist() == Dist.CLIENT;
    }

    @Override
    public String getLoader() {
        return "neoforge";
    }

    @Override
    public Path getPath(String string) {
        return FMLLoader.getCurrent().getGameDir();
    }

    @Override
    public boolean isModPresent(String mod) {
        return ModList.get().isLoaded(mod);
    }
}
