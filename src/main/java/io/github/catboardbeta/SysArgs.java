package io.github.catboardbeta;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

@SuppressWarnings("unused")
public final class SysArgs {

    /**
     * @param id the 'name' of an option. Example: '3'
     *           would mean "-3", 'x' : '-x', etc
     * @param type if false = option, if true = parameter
     */
    private record ArgumentId(char id, boolean type) {}

    /**
     * Store two completely unrelated values together.
     *
     * @param <T1> Type of the first value
     * @param <T2> Type of the second value
     */
    private record Tuple<T1, T2>(T1 first, T2 second) {}

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
     * @param args All the arguments pass in
     *             {@code static void main(String[] args)}
     * @param root The root class, as in the class containing
     *             {@code static void main(String[ args}
     */
    public SysArgs(final String[] args, final SysArgsExecutable root) {

        // Arguments are parsed as a single String object
        StringBuilder joinedArgs = new StringBuilder();
        for (String arg : args) {
            joinedArgs.append(arg).append(" ");
        }

        this.baseArgs = joinedArgs.toString();
        this.root = root;
    }

    /**
     * Parse all  the parameters and options added with
     * {@link #addOption(char)} and {@link #addParameter(char)},
     * calls {@link SysArgsExecutable#call(HashMap, TreeMap)} when complete.
     * <br><br>
     * Any options or parameters added after the fact will be ignored.
     *
     * @throws Throwable when {@link SysArgsExecutable#call(HashMap, TreeMap)}
     */
    public void parseArgs() throws Throwable {
        // Easier to parse from chars than single String
        final char[] argsArr = this.baseArgs.toCharArray();

        final HashMap<Character, Boolean> parsedOptions   = new HashMap<>();
        final TreeMap<Character, String> parsedParameters = new TreeMap<>();

        for (ArgumentId arg : parsableArgs) {
            if (!arg.type()) {
                parsedOptions.put(arg.id, null);
            } else {
                parsedParameters.put(arg.id, null);
            }
        }

        int treeIndex = 0;
        final int treeTotal = parsedParameters.size();
        for (int i = 0; i < argsArr.length; i++) {
            final char c = argsArr[i];
            if (c == '-') {
                do {
                    if (parsedOptions.containsKey(argsArr[i + 1])) {
                        parsedOptions.replace(argsArr[i + 1], true);
                        i++;
                    } else {
                        System.out.println("Option " + c + "is not applicable");
                        System.exit(1);
                    }
                } while (!Character.isWhitespace(argsArr[i + 1]));
            } else if (c == ' ') {
                continue;
            } else {
                if (treeIndex >= treeTotal) {
                    System.out.println("Expected parameter count: "
                            + treeTotal
                            + ", got ≥ "
                            + (treeTotal + 1) + "!");
                    System.exit(1);
                }

                String restOfArgString = Arrays.toString(
                        Arrays.copyOfRange(argsArr, i, argsArr.length));
                Tuple<String, Integer> tillWhite =
                        tillWhitespace(restOfArgString);

                System.out.println(parsedParameters);
                parsedParameters.replace(
                        (Character) parsedParameters
                                .keySet()
                                .toArray()[treeIndex],
                        tillWhite.first());
                i += tillWhite.second();
                treeIndex++;
            }
        }

        if (treeIndex < treeTotal) {
            System.out.println("Expected " + treeTotal + " parameters, but "
                    + "only got " + treeIndex + '!');
            System.exit(1);
        }

        this.root.call(parsedOptions, parsedParameters);
    }


    @Contract(pure = true)
    private static @NotNull Tuple<String, Integer> tillWhitespace(
            final @NotNull String restOfArgString) {
        final char[] cArr      = restOfArgString.toCharArray();
        int          charCount = 0;
        StringBuilder finalString = new StringBuilder();

        for (char c : cArr) {
            if (Character.isWhitespace(c)) {
                break;
            } else {
                finalString.append(c);
                charCount++;
            }
        }

        return new Tuple<>(finalString.toString(), charCount);
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
