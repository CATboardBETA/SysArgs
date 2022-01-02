package io.github.catboardbeta;

import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * A part of the SysArgs project
 * <br>
 * <br>
 * All main classes using SysArgs for argument parsing are required
 * to implement this class, else it throws {@link IllegalArgumentException}.
 */
@SuppressWarnings("unused")
public interface SysArgsExecutable {

    /**
     * Code to be run when {@link SysArgs#parseArgs()} is complete.
     */
    @Contract("null, _ -> fail; _, null -> fail;")
    @SuppressWarnings("RedundantThrows")
    default void call(
            HashMap<
                    Character,
                    Boolean> options,
            TreeMap<
                                Character,
                                String> parameters
    ) throws Throwable {
        throw new RequiresImplementationError("Classes implementing " +
                "io.github.catboardbeta.SysArgsExecutable must implement " +
                "method call(...)");
    }

}
