package lib_2.java_api_extensions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileX {

    public static BufferedReader get_file_lines(String path)
            throws FileNotFoundException {
        File file = new File(path);
        FileReader file_reader = new FileReader(file);
        return new BufferedReader(file_reader);
    }
}
