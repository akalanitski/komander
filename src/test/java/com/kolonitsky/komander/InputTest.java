package com.kolonitsky.komander;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by akalanitski on 22.07.2018.
 */
public class InputTest {

	//--------------------
	// Flags Definition
	//--------------------

	@Test
	public void testFlags() throws Exception {
		Input in = new Input();
		in.addFlagDefinition("--foo", "Sample flag");
		in.addFlagDefinition("--bar", "Sample falg");
		in.parseArguments(new String[] {"--foo"});
		Assert.assertTrue(in.hasFlag("--foo"));
		Assert.assertFalse(in.hasFlag("--bar"));
	}

	@Test(expected = KomanderException.class)
	public void testDoubleDefinition() throws Exception {
		Input in = new Input();
		in.addFlagDefinition("--foo", "Sample flag");
		in.addFlagDefinition("--foo", "Sample flag");
	}

	@Test
	public void testSetFlag() throws Exception {
		Input in = new Input();
		in.addFlagDefinition("--foo", "Sample flag");
		in.setFlag("--foo");
		Assert.assertTrue(in.hasFlag("--foo"));
		in.clearFlag("--foo");
		Assert.assertFalse(in.hasFlag("--foo"));
	}

	@Test (expected = KomanderException.class)
	public void testSetUndefiniedFlag() throws Exception {
		Input in = new Input();
		in.addFlagDefinition("--foo", "Sample flag");
		in.setFlag("--bar");
	}


	//----------------------
	// Parameters Definition
	//----------------------

	@Test
	public void testParameter() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("username", null, "User name", null);
		in.parseArguments(new String[] {"Alexey"});
		Assert.assertEquals("Alexey", in.getParameter("username"));
	}

	@Test
	public void testTwoParameter() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("firstName", null, "First name", null);
		in.addParameterDefinition("secondName", null, "Second name", null);
		in.parseArguments(new String[] {"Alexey", "Kolonitsky"});
		Assert.assertEquals("Alexey", in.getParameter("firstName"));
		Assert.assertEquals("Kolonitsky", in.getParameter("secondName"));
	}

	@Test
	public void testTwoParameterAndOption() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("firstName", null, "First name", null);
		in.addParameterDefinition("secondName", null, "Second name", null);
		in.addParameterDefinition("email", null, "User email", "--email");
		in.parseArguments(new String[] {"Alexey", "Kolonitsky", "--email", "alexey.s.kolonitsky@gmail.com"});
		Assert.assertEquals("Alexey", in.getParameter("firstName"));
		Assert.assertEquals("Kolonitsky", in.getParameter("secondName"));
		Assert.assertEquals("alexey.s.kolonitsky@gmail.com", in.getParameter("email"));
	}

	@Test
	public void testOptionalParameter() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("username", "Cfif", "User name", null);
		in.parseArguments(new String[] {});
		Assert.assertEquals("Cfif", in.getParameter("username"));
	}

	@Test
	public void testParameterWithPrefix() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("username", null, "User name", "-u");
		in.parseArguments(new String[] {"-u", "Alexey"});
		Assert.assertEquals("Alexey", in.getParameter("username"));
	}

	@Test(expected = KomanderException.class)
	public void testParameterDoubleDefinition() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("username", null, "User name", null);
		in.addParameterDefinition("username", null, "User name", null);
		in.parseArguments(new String[] {"Alexey"});
		Assert.assertEquals("Alexey", in.getParameter("username"));
	}

	//----------------------
	// Flags processing
	//----------------------

	@Test
	public void testUnknownFlags() throws Exception {
		Input in = new Input();
		in.addFlagDefinition("--foo", "Sample flag");
		in.parseArguments(new String[] {"--super"});
		Assert.assertFalse(in.hasFlag("--foo"));
	}

	@Test
	public void testDoubleFlag() throws Exception {
		Input in = new Input();
		in.addFlagDefinition("--foo", "Sample flag");
		in.parseArguments(new String[] {"--foo", "--foo"});
		Assert.assertTrue(in.hasFlag("--foo"));
	}

	//----------------------
	// Documentation
	//----------------------

	@Test
	public void testUsageDocumentation() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("hostname", null, "Hostname used to connect to database", null);
		in.addParameterDefinition("port", "3300", "port", "-p");
		in.addParameterDefinition("user", null, "Username", "-u");
		in.addParameterDefinition("auth", null, "Authentication key", "-a");
		in.addFlagDefinition("--foo", "Sample flag");

		Assert.assertEquals("<hostname> [-p <port>] -u <user> -a <auth> [--foo] ", in.definitionString());
	}

	@Test
	public void testFullDocumentation() throws Exception {
		Input in = new Input();
		in.addParameterDefinition("hostname", null, "Hostname used to connect to database", null);
		in.addParameterDefinition("port", "3300", "port", "-p");
		in.addParameterDefinition("user", null, "Username", "-u");
		in.addParameterDefinition("auth", null, "Authentication key", "-a");
		in.addFlagDefinition("--foo", "Sample flag");

		Assert.assertEquals("<hostname> [-p <port>] -u <user> -a <auth> [--foo] \n" +
				"Parameters:\n" +
				"\t      <hostname> - Hostname used to connect to database\n" +
				"\t       -p <port> - (default value = 3300)port\n" +
				"\t       -u <user> - Username\n" +
				"\t       -a <auth> - Authentication key\n" +
				"Flags:\n" +
				"\t           --foo - Sample flag", in.documentationString());
	}

}