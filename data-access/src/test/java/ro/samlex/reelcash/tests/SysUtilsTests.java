package ro.samlex.reelcash.tests;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;
import ro.samlex.reelcash.Reelcash;
import ro.samlex.reelcash.SysUtils;

public class SysUtilsTests {

    @Test
    public void getUserHome_returnsUserHome() {
        String expected = System.getProperty("user.home");

        String actual = SysUtils.getUserHome();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void getUserSettingsPath_onAnyOperatingSystem_returnsPathToADirectory() {
        assertTrue(Files.isDirectory(SysUtils.getUserSettingsPath()));
    }

    @Test
    public void getUserSettingsPath_onWindows_returnsExpectedPath() {
        String os = System.getProperty("os.name");
        final String expected = FileSystems.getDefault().getPath(System.getenv("APPDATA"), Reelcash.APPLICATION_NAME).toAbsolutePath().toString();
        try {
            System.setProperty("os.name", "Windows");
            assertEquals(expected, SysUtils.getUserSettingsPath().toString());
        } finally {
            System.setProperty("os.name", os);
        }
    }

    @Test
    public void getUserSettingsPath_onLinux_returnsExpectedPath() {
        String os = System.getProperty("os.name");
        final String expected = FileSystems.getDefault()
                .getPath(SysUtils.getUserHome(), ".local", "share", Reelcash.APPLICATION_NAME)
                .toAbsolutePath().toString();
        try {
            System.setProperty("os.name", "Linux");
            assertEquals(expected, SysUtils.getUserSettingsPath().toString());
        } finally {
            System.setProperty("os.name", os);
        }
    }

    @Test
    public void getUserSettingsPath_onARandomOS_returnsExpectedPath() {
        String os = System.getProperty("os.name");
        final String expected = FileSystems.getDefault()
                .getPath(SysUtils.getUserHome(), "." + Reelcash.APPLICATION_NAME)
                .toAbsolutePath().toString();
        try {
            System.setProperty("os.name", "AbxbSUHXSBkAKAAK");
            assertEquals(expected, SysUtils.getUserSettingsPath().toString());
        } finally {
            System.setProperty("os.name", os);
        }
    }

    @Test
    public void getDbFolderPath_inAnyCase_returnsFolderRelativeToUserSettingsPath() {
        Path dbFolderPath = SysUtils.getDbFolderPath();
        Path userSettingsPath = SysUtils.getUserSettingsPath();

        assertEquals("db", userSettingsPath.relativize(dbFolderPath).toString());
    }

    @Test
    public void ensureDir_whenDirectoryDoesNotExist_createsDirectory() {
        try {
            Path dirPath = Files.createTempDirectory("abc");
            Files.delete(dirPath);

            SysUtils.ensureDirs(dirPath);

            assertTrue(Files.exists(dirPath));
            assertTrue(Files.isDirectory(dirPath));
            Files.deleteIfExists(dirPath);
        } catch (IOException ex) {
            fail(ex.getMessage());
        } 
    }
    
    @Test
    public void ensureDir_inHappyFlow_returnsAbsolutePathToDirectory() throws IOException {
        Path aPath = FileSystems.getDefault().getPath("abc");
        try {
            Path actual = SysUtils.ensureDirs(aPath);
            assertTrue(actual.isAbsolute());
        } catch (IOException e) {
            fail(e.getMessage());
        } finally {
            Files.deleteIfExists(aPath);
        }
    }
}
