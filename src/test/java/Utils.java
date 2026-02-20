import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Properties;

public class Utils {
    public static void setEnv(String key,String value) throws ConfigurationException {
        //sensitive token or information gulo k comfig properties er modhe rakhi
        PropertiesConfiguration config=new PropertiesConfiguration("src/test/resources/config.properties");
        config.setProperty(key,value);
        config.save();


    }
}
