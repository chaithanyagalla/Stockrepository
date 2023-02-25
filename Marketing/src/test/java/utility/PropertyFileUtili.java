package utility;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtili {
	public static Properties config;

public static String getValueForKey(String keys) throws Throwable {
	Properties config=new Properties();
	config.load(new FileInputStream("D:\\qedg\\Marketing\\EnvironmentProperty\\File.properties"));
	return config.getProperty(keys);
}

}
