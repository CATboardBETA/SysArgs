package io.github.catboardbeta;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class RequiresImplementationError extends Error {

    /**
     * Throw a new RequiresImplementationError.
     */
    public RequiresImplementationError() {
        super();
    }

    /**
     * Throw a new RequiresImplementationError.
     *
     * @param message A detailed message for why this {@link Error} was thrown.
     */
    public RequiresImplementationError(final String message) {
        super(message);
    }

    /**
     * Throw a new RequiresImplementationError.
     *
     * @param message A detailed message for why this {@link Error} was thrown.
     * @param cause   If this error was thrown from a catch block, whatever
     *                exception was caught is the cause.
     */
    public RequiresImplementationError(final String message,
                                       final @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Throw a new RequiresImplementationError.
     *
     * @param cause If this error was thrown from a catch block, whatever
     *              exception was caught is the cause.
     */
    public RequiresImplementationError(final @Nullable Throwable cause) {
        super(cause);
    }

    /**
     * Throw a new RequiresImplementationError.
     *
     * @param message            A detailed message for why this {@link Error}
     *                           was thrown.
     * @param cause              If this error was thrown from a catch block,
     *                           whatever exception was caught is the cause.
     * @param enableSuppression  also show past caught exception added to list
     *                           of suppressed exceptions
     * @param writableStackTrace Whether the Stack Trace should be writable
     */
    public RequiresImplementationError(final String message,
                                       final @Nullable Throwable cause,
                                       final boolean enableSuppression,
                                       final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
