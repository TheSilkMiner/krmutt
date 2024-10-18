package net.thesilkminer.krmutt.javamix.mixer;

import net.thesilkminer.krmutt.javamix.JavaMixer;
import org.jetbrains.annotations.NotNull;

public final class ModuleMixer extends JavaMixer {
    private static final ModuleMixer INSTANCE = new ModuleMixer();

    private ModuleMixer() {
        super("module", 9);
    }

    @NotNull
    public static ModuleMixer get() {
        return INSTANCE;
    }

    public boolean isModuleEnvironment() {
        return this.getClass().getModule().isNamed();
    }
}
