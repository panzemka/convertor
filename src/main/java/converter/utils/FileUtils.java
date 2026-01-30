package converter.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    public static String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            return "";
        }

        return filename.substring(dotIndex + 1).toLowerCase();
    }

    public static boolean isSupportedFormat(String filename) {
        String ext = getFileExtension(filename);
        return ext.equals("json") || ext.equals("xml") || ext.equals("csv");
    }

    public static void ensureDirectoryExists(String filePath) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            if (!parent.mkdirs()) {
                throw new IOException("Failed to create directory: " + parent.getAbsolutePath());
            }
        }
    }

    public static String readFileContent(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    public static void writeFileContent(File file, String content) throws IOException {
        ensureDirectoryExists(file.getAbsolutePath());
        Files.writeString(file.toPath(), content);
    }

    public static void backupFile(File file) throws IOException {
        if (!file.exists()) {
            return;
        }

        String backupPath = file.getAbsolutePath() + ".backup";
        Path source = file.toPath();
        Path target = new File(backupPath).toPath();

        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    public static long getFileSize(File file) {
        return file.length();
    }

    public static String formatFileSize(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.1f KB", bytes / 1024.0);
        } else {
            return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
        }
    }
}