package com.kolonitsky.komander;

import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by akalanitski on 12.08.2018.
 */
public class DependencyCollectionTest {

	@Test
	public void addConfigurator() throws Exception {
		Configurator c = new Configurator();
		c.readConfig(TestConstants.propFile);
	}

	@Test
	public void hasConfigurator() throws Exception {

	}

	@Test
	public void removeConfigurator() throws Exception {

	}

	@Test
	public void getConfigProperty() throws Exception {
		Configurator c = new Configurator();
		c.readConfig(TestConstants.propFile);
		DependencyCollection col = new DependencyCollection();
		col.addConfigurator(c);
		assertTrue(col.getConfigProperty("project.id").equals("myProject"));
	}

	@Test
	public void hasConfiguratorProperty() throws Exception {

	}

	@Test
	public void addInstance() throws Exception {
		DependencyCollection c = new DependencyCollection();
		c.addInstance("val", new ValueClass("Hello World"));
		Assert.assertTrue(c.hasDependency("val"));
		Assert.assertFalse(c.hasDependency("invalidKey"));
	}

	@Test
	public void getDependency() throws Exception {
		DependencyCollection c = new DependencyCollection();
		c.addInstance("val", new ValueClass("Hello World"));
		Assert.assertTrue(c.getDependency("val").key.equals("val"));
	}

}

class ValueClass {
	private String value = "";
	public ValueClass(String value) { this.value = value; }
}