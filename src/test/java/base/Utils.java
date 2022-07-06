package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static String getDataFromPropertiesFile(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src\\test\\java\\resources\\url.properties");
        prop.load(fis);
        String value = prop.getProperty(key);
        return value;
    }
}
