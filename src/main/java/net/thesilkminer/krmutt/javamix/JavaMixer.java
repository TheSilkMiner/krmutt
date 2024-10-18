package net.thesilkminer.krmutt.javamix;

import io.github.oshai.kotlinlogging.KLogger;
import io.github.oshai.kotlinlogging.KotlinLogging;
import org.jetbrains.annotations.NotNull;

public abstract class JavaMixer {
    @NotNull protected final KLogger logger;
    @NotNull private final String mixerName;

    protected JavaMixer(@NotNull final String mixerId, final int targetJava) {
        final String mixerName = mixerId + "-mixer-j" + targetJava;
        this.logger = KotlinLogging.INSTANCE.logger(mixerName);
        this.mixerName = mixerName;

        this.logger.debug(() -> "Initialized mixer \"" + mixerId + "\" for Java version " + targetJava);
    }

    @Override
    public final String toString() {
        return this.mixerName;
    }
}
