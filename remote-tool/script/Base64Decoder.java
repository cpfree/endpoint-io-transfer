
import java.io.*;
import java.nio.file.FileSystemException;
import java.util.Base64;

public class Base64Decode {

    /**
     * convert Base64 to a file
     */
    public static void main(String[] args) throws IOException {
        final String filePath = args[0];
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("file not found ==> " + filePath);
        }
        if (!file.isFile()) {
            throw new FileSystemException("is not a file ==> " + filePath);
        }
		String savePath = "";
        if (args[1] == null || args[1].trim().isEmpty()) {
			savePath = file.getParentFile().getPath() + File.separator + "base64DecodeFile";
		} else {
			savePath = args[1];
		}
        try (FileReader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            final int read = reader.read(chars);
            final String base64 = new String(chars).trim();
            byte[] bytes = Base64.getDecoder().decode(base64);
            writeFile(savePath, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(String savePath, byte[] content) {
        final File file = new File(savePath);
        try (final FileOutputStream writer = new FileOutputStream(file)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
