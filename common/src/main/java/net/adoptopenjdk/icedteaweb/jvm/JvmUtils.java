package net.adoptopenjdk.icedteaweb.jvm;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static net.adoptopenjdk.icedteaweb.IcedTeaWebConstants.JAVAWS;
import static net.adoptopenjdk.icedteaweb.JavaSystemPropertiesConstants.ITW_BIN_LOCATION;
import static net.adoptopenjdk.icedteaweb.JavaSystemPropertiesConstants.ITW_BIN_NAME;
import static net.adoptopenjdk.icedteaweb.StringUtils.isBlank;

public class JvmUtils {

    private static final Set<String> VALID_VM_ARGUMENTS = unmodifiableSet(new HashSet<>(asList(getValidVMArguments())));
    private static final List<String> VALID_STARTING_ARGUMENTS = unmodifiableList(asList(getValidStartingVMArguments()));
    private static final List<String> VALID_SECURE_PROPERTIES = unmodifiableList(asList(getValidSecureProperties()));

    /**
     * Check that the VM args are valid and safe.
     * <p>
     * Based on
     * http://java.sun.com/javase/6/docs/technotes/guides/javaws/developersguide/syntax.html
     *
     * @param vmArgs a string containing the args
     * @throws IllegalArgumentException if the VM arguments are invalid or dangerous
     */
    public static void checkVMArgs(final String vmArgs) throws IllegalArgumentException {
        if (isBlank(vmArgs)) {
            return;
        }

        final String[] arguments = vmArgs.split(" ");
        for (String argument : arguments) {
            if (isInvalidValidArgument(argument)) {
                throw new IllegalArgumentException(argument);
            }
        }
    }

    private static boolean isInvalidValidArgument(final String argument) {
        return !VALID_VM_ARGUMENTS.contains(argument) && !isValidStartingArgument(argument);
    }

    private static boolean isValidStartingArgument(final String argument) {
        for (String validStartingArgument : VALID_STARTING_ARGUMENTS) {
            if (argument.startsWith(validStartingArgument)) {
                return true;
            }
        }
        return false;
    }

    static boolean isValidSecureProperty(final String argument) {
        if (VALID_SECURE_PROPERTIES.contains(argument)) {
            return true;
        }
        return false;
    }

    /**
     * Returns an array of valid (ie safe and supported) arguments for the JVM
     * <p>
     * Based on
     * http://java.sun.com/javase/6/docs/technotes/guides/javaws/developersguide/syntax.html
     */
    private static String[] getValidVMArguments() {
        return new String[]{
                "-d32", /* use a 32-bit data model if available */
                "-client", /* to select the client VM */
                "-server", /* to select the server VM */
                "-verbose", /* enable verbose output */
                "-version", /* print product version and exit */
                "-showversion", /* print product version and continue */
                "-help", /* print this help message */
                "-X", /* print help on non-standard options */
                "-ea", /* enable assertions */
                "-enableassertions", /* enable assertions */
                "-da", /* disable assertions */
                "-disableassertions", /* disable assertions */
                "-esa", /* enable system assertions */
                "-enablesystemassertions", /* enable system assertions */
                "-dsa", /* disable system assertione */
                "-disablesystemassertions", /* disable system assertione */
                "-Xmixed", /* mixed mode execution (default) */
                "-Xint", /* interpreted mode execution only */
                "-Xnoclassgc", /* disable class garbage collection */
                "-Xincgc", /* enable incremental garbage collection */
                "-Xbatch", /* disable background compilation */
                "-Xprof", /* output cpu profiling data */
                "-Xdebug", /* enable remote debugging */
                "-Xfuture", /* enable strictest checks, anticipating future default */
                "-Xrs", /* reduce use of OS signals by Java/VM (see documentation) */
                "-XX:+ForceTimeHighResolution", /* use high resolution timer */
                "-XX:-ForceTimeHighResolution", /* use low resolution (default) */};
    }

    /**
     * Returns an array containing the starts of valid (ie safe and supported)
     * arguments for the JVM
     * <p>
     * Based on
     * http://java.sun.com/javase/6/docs/technotes/guides/javaws/developersguide/syntax.html
     */
    private static String[] getValidStartingVMArguments() {
        return new String[]{
                "-ea", /* enable assertions for classes */
                "-enableassertions", /* enable assertions for classes */
                "-da", /* disable assertions for classes */
                "-disableassertions", /* disable assertions for classes */
                "-verbose", /* enable verbose output */
                "-Xms", /* set initial Java heap size */
                "-Xmx", /* set maximum Java heap size */
                "-Xss", /* set java thread stack size */
                "-XX:NewRatio", /* set Ratio of new/old gen sizes */
                "-XX:NewSize", /* set initial size of new generation */
                "-XX:MaxNewSize", /* set max size of new generation */
                "-XX:PermSize", /* set initial size of permanent gen */
                "-XX:MaxPermSize", /* set max size of permanent gen */
                "-XX:MaxHeapFreeRatio", /* heap free percentage (default 70) */
                "-XX:MinHeapFreeRatio", /* heap free percentage (default 40) */
                "-XX:UseSerialGC", /* use serial garbage collection */
                "-XX:ThreadStackSize", /* thread stack size (in KB) */
                "-XX:MaxInlineSize", /* set max num of bytecodes to inline */
                "-XX:ReservedCodeCacheSize", /* Reserved code cache size (bytes) */
                "-XX:MaxDirectMemorySize",};
    }

    /**
     * https://docs.oracle.com/javase/8/docs/technotes/guides/javaws/developersguide/syntax.html#secure-property
     * <p>
     * Properties set in the JNLP file will normally be set by Web Start after the VM is started but before
     * the application is invoked. These properties are considered "secure" properties and can be passed as
     * -Dkey=value arguments on the java invocation command line. The following properties are predefined secure
     * properties and will be passed to the VM in this way.
     *
     * @return
     */
    private static String[] getValidSecureProperties() {
        return new String[]{
                "sun.java2d.noddraw",
                "javaws.cfg.jauthenticator",
                "swing.useSystemFontSettings",
                "swing.metalTheme",
                "http.agent",
                "http.keepAlive",
                "sun.awt.noerasebackground",
                "sun.java2d.opengl",
                "sun.java2d.d3d",
                "java.awt.syncLWRequests",
                "java.awt.Window.locationByPlatform",
                "sun.awt.erasebackgroundonresize",
                "sun.awt.keepWorkingSetOnMinimize",
                "swing.noxp",
                "swing.boldMetal",
                "awt.useSystemAAFontSettings",
                "sun.java2d.dpiaware",
                "sun.awt.disableMixing",
                "sun.lang.ClassLoader.allowArraySyntax",
                "java.awt.smartInvalidate",
                "apple.laf.useScreenMenuBar",
                "java.net.preferIPv4Stack",
                "java.util.Arrays.useLegacyMergeSort",
                "sun.locale.formatasdefault",
                "sun.awt.enableExtraMouseButtons",
                "com.sun.management.jmxremote.local.only",
                "sun.nio.ch.bugLevel",
                "sun.nio.ch.disableSystemWideOverlappingFileLockCheck",
                "jdk.map.althashing.threshold",
                "javaplugin.lifecycle.cachesize",
                "http.maxRedirects",
                "http.auth.digest.validateProxy",
                "http.auth.digest.validateServer"
        };
    }

    /**
     * @return the javaws binary.
     */
    public static String getJavaWsBin() {
        //Shortcut executes the jnlp as it was with system preferred java. It should work fine offline
        //absolute - works in case of self built
        final String exec = System.getProperty(ITW_BIN_LOCATION);
        if (exec != null) {
            return exec;
        }
        final String pathResult = findOnPath(new String[]{JAVAWS, System.getProperty(ITW_BIN_NAME)});
        if (pathResult != null) {
            return pathResult;
        }

        return JAVAWS;
    }

    /**
     * Find a binary of the given binaries on PATH.
     *
     * @param bins list of binaries to look for
     * @return the absolute path to the first binary found
     */
    public static String findOnPath(final String[] bins) {
        String exec = null;
        //find if one of binaries is on path
        String path = System.getenv().get("PATH");
        if (path == null || path.trim().isEmpty()) {
            path = System.getenv().get("path");
        }
        if (path == null || path.trim().isEmpty()) {
            path = System.getenv().get("Path");
        }
        if (path != null && !path.trim().isEmpty()) {
            //relative - works with alternatives
            final String[] paths = path.split(File.pathSeparator);
            outerloop:
            for (String bin : bins) {
                //when property is not set
                if (bin == null) {
                    continue;
                }
                for (String p : paths) {
                    final File file = new File(p, bin);
                    if (file.exists()) {
                        exec = file.getAbsolutePath();
                        break outerloop;
                    }
                }

            }
        }
        return exec;
    }
}
