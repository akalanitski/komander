package com.kolonitsky.komander;

import java.io.*;
import java.util.Properties;

/**
 * Configuration file witch used to read/write properties between application
 * runs. And to define properties which also will be available in the commands
 * from IOC
 *
 * @author Alexey Kolonitsky &lt;alexey.s.kolonitsky@gmail.com&gt;
 * @since 18.08.2017.
 */
public class Configurator {

	Properties _properties = new Properties();
	public Configurator() {
	}

	public void set(String key, String value) {
		if (_properties == null || key != null && !key.isEmpty())
		_properties.setProperty(key, value);
	}

	public String get(String key) {
		if (_properties != null && key != null && !key.isEmpty()) {
			Object value = _properties.get(key);
			if (value != null)
				return value.toString();
		}
		return null;
	}

	public boolean has(String key) {
		if (_properties != null && key != null && !key.isEmpty())
			return _properties.containsKey(key);
		return false;
	}

	public void readConfig(String _filename) {
		InputStream result = null;
		try {
			result = new FileInputStream(_filename);
		} catch (IOException exception) {
			exception.printStackTrace();
			return;
		}

		try {
			_properties.load(result);
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public String[] getProperties() {
		int length = _properties.size();
		Object[] keys = _properties.keySet().toArray();
		String[] result = new String[length];
		for (int i = 0; i < length; i++)
			result[i] = (String) keys[i];
		return result;
	}
}
