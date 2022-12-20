package ru.sfedu.ceramicshop.utils;

import ru.sfedu.ceramicshop.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration utility. Allows to get configuration properties from the
 * default configuration file
 *
 * @author Boris Jmailov
 */
public class ConfigurationUtil {

    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.properties";
    private static final Properties configuration = new Properties();
    private static final String CUSTOM_CONFIG_PATH = System.getProperty("configPath");

    /**
     * Hides default constructor
     */
    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) {
            loadConfiguration();
        }
        return configuration;
    }

    /**
     * Loads configuration from <code>DEFAULT_CONFIG_PATH</code>
     *
     * @throws IOException In case of the configuration file read failure
     */
    private static void loadConfiguration() throws IOException {
        File nf;
        if (System.getProperty(Constants.CONFIG_PATH) != null) {
            nf = new File(System.getProperty(Constants.CONFIG_PATH));
        } else {
            nf = new File(DEFAULT_CONFIG_PATH);
        }
        // DEFAULT_CONFIG_PATH.getClass().getResourceAsStream(DEFAULT_CONFIG_PATH);
        try (InputStream in = new FileInputStream(nf)) {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    /**
     * Gets configuration entry value
     *
     * @param key Entry key
     * @return Entry value by key
     * @throws IOException In case of the configuration file read failure
     */
    public static String getConfigurationEntry(String key) throws IOException {
        return getConfiguration().getProperty(key);
    }
}
