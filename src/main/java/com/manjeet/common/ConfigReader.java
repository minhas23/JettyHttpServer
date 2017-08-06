package com.manjeet.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class.getCanonicalName());
	
	private static Properties prop = new Properties();
	
	private static volatile ConfigReader config = new ConfigReader();

	private ConfigReader(){
		load();
	}
	
	private void load() {

		try {
	           //load a properties file from class path, inside static method
			prop.load(getClass().getClassLoader().getResourceAsStream("server.properties"));

		} catch (Exception e) {
			logger.error("Exception in ConfigReader.load(): " + e.getMessage(), e);
	    }
		
	}

	public static ConfigReader getInstance(){
		return config;
	}
	
	public static Properties getProperties(){
		return prop;
	}
	
	 public static int getIntProperty(Properties props, String propName, int defaultValue)
	    {
	        Object value = props.get(propName);
	        if(value == null) return defaultValue;
	        if( value instanceof Integer)
	        {
	            return (Integer) value;
	        }
	        String strValue = String.valueOf(value);
	        if (strValue.isEmpty())
	        {
	            return defaultValue;
	        }
	        return Integer.parseInt(strValue.trim());
	    }

	    public static long getLongProperty(Properties props, String propName, long defaultValue)
	    {
	        Object value = props.get(propName);
	        if(value == null) return defaultValue;
	        if( value instanceof Long)
	        {
	            return (Long) value;
	        }
	        String strValue = String.valueOf(value);
	        if (strValue.isEmpty())
	        {
	            return defaultValue;
	        }
	        return Long.parseLong(strValue.trim());
	    }

	    public static float getFloatProperty(Properties props, String propName, float defaultValue)
	    {
	        Object value = props.get(propName);
	        if(value == null) return defaultValue;
	        if( value instanceof Float)
	        {
	            return (Float) value;
	        }
	        String strValue = String.valueOf(value);
	        if (strValue.isEmpty())
	        {
	            return defaultValue;
	        }
	        return Float.parseFloat(strValue.trim());
	    }

	    public static boolean getBooleanProperty(Properties props, String propName, boolean defaultValue)
	    {
	        Object value = props.get(propName);
	        if(value == null) return defaultValue;
	        if( value instanceof Boolean)
	        {
	            return (Boolean) value;
	        }
	        String strValue = String.valueOf(value);
	        if (strValue.isEmpty())
	        {
	            return defaultValue;
	        }
	        return Boolean.parseBoolean(strValue.trim());
	    }

	    public static String getStringProperty(Properties props, String propName, String defaultValue)
	    {
	        String value = props.getProperty(propName);
	        if (value == null || value.isEmpty())
	        {
	            return defaultValue;
	        }
	        return value.trim();
	    }

	    public static double getDoubleProperty(Properties props, String propName, double defaultValue)
	    {
	        Object value = props.get(propName);
	        if(value == null) return defaultValue;
	        if( value instanceof Double)
	        {
	            return (Double) value;
	        }
	        String strValue = String.valueOf(value);
	        if (strValue.isEmpty())
	        {
	            return defaultValue;
	        }
	        return Double.parseDouble(strValue.trim());
	    }

	    public static List<String> getListProperty(Properties props, String propName, String defaultValue)
	    {
	        String value = props.getProperty(propName);
	        if (value == null || value.isEmpty())
	        {
	            if ((defaultValue == null) || defaultValue.isEmpty())
	            {
	                return new ArrayList<String>();
	            }
	            else
	            {
	                value = defaultValue;
	            }
	        }
	        return Arrays.asList(value.split(","));

	    }

	    public static String[] getArrayProperty(Properties props, String propName, String defaultValue)
	    {
	        String value = props.getProperty(propName);
	        if (value == null || value.isEmpty())
	        {
	            if ((defaultValue == null) || defaultValue.isEmpty())
	            {
	                return new String[]{};
	            }
	            else
	            {
	                value = defaultValue;
	            }
	        }
	        return value.split(",");

	    }

	
	
	public static void main(String[] args) {
		System.out.println(ConfigReader.getIntProperty(ConfigReader.getProperties(), "jetty.server.port", 8000));
//	   ConfigReader env = ConfigReader.getInstance();
//	   System.out.println(env.getProperties());
	}
	
}
