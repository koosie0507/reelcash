package ro.samlex.reelcash;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SysUtils {

    public static Path getUserHome() {
        return FileSystems.getDefault().getPath(System.getProperty("user.home"));
    }

    public static Path getUserSettingsPath() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Linux")) {
            return createPath(getUserHome().toString(), ".local", "share", Reelcash.APPLICATION_NAME);
        }
        if (osName.startsWith("Windows")) {
            return createPath(System.getenv("APPDATA"), Reelcash.APPLICATION_NAME);
        }

        return FileSystems
                .getDefault()
                .getPath(getUserHome().toString(), "." + Reelcash.APPLICATION_NAME)
                .toAbsolutePath();
    }

    public static Path getDbFolderPath() {
        return createPath(getUserSettingsPath().toString(), "db");
    }

    public static Path ensureDirs(Path directoryPath) throws IOException {
        Files.createDirectories(directoryPath);
        return directoryPath.toAbsolutePath();
    }

    private static Path createPath(String base, String... additionalParts) {
        return FileSystems.getDefault().getPath(base, additionalParts).toAbsolutePath();
    }
}
