package testExample;

import io.github.catboardbeta.SysArgs;
import io.github.catboardbeta.SysArgsExecutable;

import java.util.HashMap;
import java.util.TreeMap;

public class Tester implements SysArgsExecutable  {

    public static void main(String[] args) throws Throwable {
        SysArgs sArgs = new SysArgs(args, new Tester());
        sArgs.addOption('2').addOption('c').addParameter('x').addOption('y')
                .parseArgs();
    }

    @Override
    public void call(HashMap<Character, Boolean> options, TreeMap<Character, String> parameters) {
        System.out.println(options);
        System.out.println(parameters);
    }
}
