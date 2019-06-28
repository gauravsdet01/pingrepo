package automation.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import automation.getpageobjects.ObjectFileReader;


public class PropFileHandler extends ObjectFileReader{
	static Properties properties = new Properties();

	/**
	 * This method is used to read the value of the given property from the properties file.
	 * 
	 * @param property : the property whose value is to be fetched.
	 * @return the value of the given property.
	 */
	static String filePath = "./filename.properties";
	
	
	public static String readProperty(String property) 
	{
		InputStream inPropFile = null;
		try {
			
			inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
		} catch (IOException e) {
		}
		String value=properties.getProperty(property);	
		
		return value;
	}

/**
 * This method is used to write the value of the property in property file.
 * @param property
 * @param value
 * @throws IOException
 */
	public static void writeToFile(String property, String value)  {
		try {
			InputStream inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
			inPropFile.close();
			OutputStream outPropFile = new FileOutputStream(filePath);
			properties.setProperty(property, value);
			properties.store(outPropFile, null);
			outPropFile.close();
		} catch (IOException e) {
			System.out.println("Unable to write value in property file");
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(String fileName ,String property, String value)  {
		try {
			InputStream inPropFile = new FileInputStream(fileName);
			properties.load(inPropFile);
			inPropFile.close();
			OutputStream outPropFile = new FileOutputStream(fileName);
			properties.setProperty(property, value);
			properties.store(outPropFile, null);
			outPropFile.close();
		} catch (IOException e) {
			System.out.println("Unable to write value in property file");
			e.printStackTrace();
		}
	}
	
}
