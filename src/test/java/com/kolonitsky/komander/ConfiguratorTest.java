package com.kolonitsky.komander;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by akalanitski on 12.08.2018.
 */
public class ConfiguratorTest {
	@Test
	public void testBase() throws Exception {
		Configurator c = new Configurator();
		c.set("project.id", "myProject");
		String val = c.get("project.id");
		Assert.assertTrue(val.equals("myProject"));
	}

	@Test
	public void getFromEmpty() throws Exception {
		Configurator c = new Configurator();
		Assert.assertNull(c.get("project.id"));
	}

	@Test
	public void testHas() throws Exception {
		Configurator c = new Configurator();
		c.set("project.id", "myProject");
		Assert.assertTrue(c.has("project.id"));
		Assert.assertFalse(c.has("fake.property"));
		Assert.assertFalse(c.has(""));
	}

	@Test
	public void readConfig() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourceUrl = classLoader.getResource("komander.properties");
		File file = new File(resourceUrl.getFile());
		System.out.println(file.getAbsolutePath());

		Configurator c = new Configurator();
		c.readConfig(file.getAbsolutePath());
		Assert.assertTrue(c.has("project.id"));
	}

}