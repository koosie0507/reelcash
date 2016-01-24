package ro.samlex.reelcash;

import java.io.File;
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

    public static String getDbFolderPath() {
        return createPath(getUserSettingsPath(), "db");
    }

    public static void mkdirs(String path) {
        File f = new File(path);
        if (f.exists()) {
            return;
        }
        f.mkdirs();
    }

    public static String createPath(String base, String... additionalParts) {
        return FileSystems.getDefault().getPath(base, additionalParts).toAbsolutePath().toString();
    }

    private static void setDefaultSettingsPath() {
        userSettingsPath = FileSystems
                .getDefault()
                .getPath(getUserHome(), ".", "." + Reelcash.APPLICATION_NAME)
                .toAbsolutePath()
                .toString();
    }

    private static boolean setLinuxSettingsPath(String osName) {
        if (!osName.startsWith("Linux")) {
            return false;
        }
        userSettingsPath = createPath(getUserHome(), ".local", "share", Reelcash.APPLICATION_NAME);
        return true;
    }

    private static boolean setWindowsSettingsPath(String osName) {
        if (!osName.startsWith("Windows")) {
            return false;
        }
        String appDataPath = System.getenv("APPDATA");
        userSettingsPath = createPath(appDataPath, Reelcash.APPLICATION_NAME);
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
