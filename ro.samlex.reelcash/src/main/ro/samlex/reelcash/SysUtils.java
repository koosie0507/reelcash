package ro.samlex.reelcash;

import java.nio.file.FileSystems;

/**
 * Class providing some patching to some of the jre's screwups.
 *
 * @author cusi
 */
public class SysUtils {
    private static String userSettingsPath;

    /**
     * Returns the home directory of the current user.
     *
     * @return home directory of current user.
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Returns the application's folder.
     *
     * @return the home folder of the application
     */
    public static String getUserSettingsPath() {
        if (userSettingsPath == null) {
            String osName = System.getProperty("os.name");
            if (!setLinuxSettingsPath(osName) && !setWindowsSettingsPath(osName)) {
                setDefaultSettingsPath();
            }
        }
        return userSettingsPath;
    }

    private static void setDefaultSettingsPath() {
        userSettingsPath = FileSystems
                .getDefault()
                .getPath(getUserHome(), ".", "." + Reelcash.APPLICATION_NAME)
                .toAbsolutePath()
                .toString();
    }

    private static boolean setLinuxSettingsPath(String osName) {
        if (!osName.startsWith("Linux")) return false;
        userSettingsPath = FileSystems
                .getDefault()
                .getPath(getUserHome(), ".local", "share", Reelcash.APPLICATION_NAME)
                .toAbsolutePath()
                .toString();
        return true;
    }

    private static boolean setWindowsSettingsPath(String osName) {
        if (!osName.startsWith("Windows")) return false;
        String appDataPath = System.getenv("APPDATA");
        userSettingsPath = FileSystems
                .getDefault()
                .getPath(appDataPath, Reelcash.APPLICATION_NAME)
                .toAbsolutePath()
                .toString();
        return true;
    }

    /**
     * Returns the current working directory.
     *
     * @return the current user's working directory
     */
    public static String getCwd() {
        return System.getProperty("user.dir");
    }
}
