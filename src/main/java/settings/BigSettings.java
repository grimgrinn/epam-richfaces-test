package settings;
//
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Настройки
 */
public class BigSettings {
    //public static final Logger MEGALOG = LogManager.getLogger(BigSettings.class);

    private static String RESOURCE_PATH = "settings.parameters";
    private static BigSettings instance = new BigSettings();
    private ResourceBundle properties;

    public static BigSettings getInstance() {
        return instance;
    }

    public BigSettings() {
        properties = ResourceBundle.getBundle(RESOURCE_PATH);
    }

    public String getValue(String key) {
        try {
            return properties.getString(key);
        } catch (MissingResourceException e) {
            System.out.println("WRONG!");
     //       MEGALOG.error("invalid setting key");
            return null;
        }
    }
}
