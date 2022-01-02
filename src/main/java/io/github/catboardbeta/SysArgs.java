package io.github.catboardbeta;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unused")
public final class SysArgs {

    /**
     * @param id the 'name' of an option. Example: '3'
     *           would mean "-3", 'x' : '-x', etc
     * <br><br>
     * @param type if false = option, if true = parameter
     */
    private record ArgumentId(char id, boolean type) { }


    /**
     * Internally represents root class.
     */
    private final SysArgsExecutable root;
    /**
     * The 'args' parameter in {@code static void main(String[] args)}.
     */
    private final String baseArgs;
    /**
     * All the options and parameters to be parsed in to.
     */
    private final ArrayList<ArgumentId> parsableArgs = new ArrayList<>();

    /**
     *
     * @param args All the arguments pass in
     *             {@code static void main(String[] args)}
     * @param root The root class, as in the class containing
     *             {@code static void main(String[ args}
     */
    public SysArgs(final String args, final SysArgsExecutable root) {
        this.baseArgs = args;
        this.root = root;
    }

    /**
     * Parse all  the parameters and options added with
     * {@link #addOption(char)} and {@link #addParameter(char)},
     * calls {@link SysArgsExecutable#call(HashMap, HashMap)} when complete.
     * <br><br>
     * Any options or parameters added after the fact will be ignored.
     */
    public void parseArgs() throws Throwable {
        char[] argsArr = this.baseArgs.toCharArray();

        HashMap<Character, Boolean> parsedOptions    = new HashMap<>();
        HashMap<Character, String>  parsedParameters = new HashMap<>();

        for (ArgumentId arg : parsableArgs) {
            if (!arg.type()) {
                parsedOptions.put(arg.id, null);
            } else {
                parsedParameters.put(arg.id, null);
            }
        }

        for (char c : argsArr) {
            if (c == '-') {
                if (parsedOptions.containsKey(c)) {
                    parsedOptions.replace(c, true);
                }
            }
        }

        this.root.call(parsedOptions, parsedParameters);
    }

    /**
     * Add an option to the parent SysArgs Object.
     * @param id id for your option
     * @return returns itself for chaining
     */
    @Contract("_ -> this")
    public SysArgs addOption(final char id) {
        parsableArgs.add(new ArgumentId(id, false));
        return this;
    }

    @Contract("_ -> this")
    public SysArgs removeOption(final char id) {
        parsableArgs.remove(new ArgumentId(id, false));
        return this;
    }

    @Contract("_ -> this")
    public SysArgs addParameter(final char id) {
        parsableArgs.add(new ArgumentId(id, true));
        return this;
    }

    @Contract("_ -> this")
    public SysArgs removeParameter(final char id) {
        parsableArgs.remove(new ArgumentId(id, true));
        return this;
    }
}
