package automation.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;


public class ConfigPropertyReader {

    private static String defaultConfigFile = "./Config.properties";
    
    
    /**
     * Constructor of this class
     */
    public ConfigPropertyReader() {
    }

    /**
     *
     * This method will get the properties pulled from a file located relative to the base dir
     *
     * @param propFile complete or relative (to base dir) file location of the properties file
     * @param Property property name for which value has to be fetched
     * @return String value of the property
     */
    public static String getProperty(String propFile, String Property) {
        try {
            Properties prop = ResourceLoader.loadProperties(propFile);
            return prop.getProperty(Property);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static String getPropertyFromFile(String Property) {
        try {
        	String path = "./src/test/resources/testdata";
            String dataFolderPath = URLDecoder.decode(path, "UTF-8");
            String outFilePath = dataFolderPath + File.separator + "courseData.properties";
            Properties prop = ResourceLoader.loadProperties(outFilePath);
            return prop.getProperty(Property);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void setProperty(String key, String Property) {
        try {
        	String path = "./src/test/resources/testdata";
            String dataFolderPath = URLDecoder.decode(path, "UTF-8");
            String outFilePath = dataFolderPath + File.separator + "courseData.properties";
            Properties prop = ResourceLoader.loadProperties(outFilePath);
            prop.setProperty(key,Property);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void setPropertyForConfigFile(String key, String Property) {
        try {
        	/*String path = "./src/test/resources/testdata";
            String dataFolderPath = URLDecoder.decode(path, "UTF-8");
            String outFilePath = dataFolderPath + File.separator + "courseData.properties";*/
            Properties prop = ResourceLoader.loadProperties(defaultConfigFile);
            prop.setProperty(key,Property);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getProperty(String property){
        return getProperty(defaultConfigFile, property);
    }
    
    public static HashMap<String,String> readAllPropertyVlauesFromConfigFile(){
        HashMap<String,String> mymap = new HashMap<String, String>();
        Properties prop;
        try {
           prop =ResourceLoader.loadProperties(defaultConfigFile);
           for (final Entry<Object, Object> entry : prop.entrySet()) {
             mymap.put((String) entry.getKey(), (String) entry.getValue());
            }
            return mymap;
        } catch (IOException e) {
            return null;
        }finally {
            //prop.c
           }
      }
}
