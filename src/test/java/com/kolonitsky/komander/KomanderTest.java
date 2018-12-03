package com.kolonitsky.komander;

import com.kolonitsky.komander.samples.EchoKomand;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by akalanitski on 03.12.2018.
 */
public class KomanderTest {

	@Test
	public void registerTest() throws Exception {
		Komander k = new Komander();
		k.register(EchoKomand.class).inputMandatoryParameter("message", null, "Print message passed as argument", null);
		k.run("echo", new String[] {"Hello world"});
	}

	@Test
	public void registerTest2() throws Exception {
		Komander k = new Komander();
		k.register(EchoKomand.class).inputMandatoryParameter("message", null, "Print message passed as argument", null);
		k.run("echo", new String[] {"Hello", "World"});
	}

}