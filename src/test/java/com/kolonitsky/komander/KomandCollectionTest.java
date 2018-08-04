package com.kolonitsky.komander;

import com.kolonitsky.komander.samples.EchoDuplicateKomand;
import com.kolonitsky.komander.samples.EchoKomand;
import com.kolonitsky.komander.samples.InvalidKomand;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by akalanitski on 22.07.2018.
 */
public class KomandCollectionTest {

	@Test
	public void regiesterNullKomand() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(null);
		Assert.assertNull(kom);
	}

	@Test
	public void regiesterKomandTwice() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom1 = col.register(EchoKomand.class);
		IKomand kom2 = col.register(EchoKomand.class);
		Assert.assertNull(kom2);
	}

	@Test
	public void regiesterEmptyNameKomand() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(InvalidKomand.class);
		Assert.assertNull(kom);
	}

	@Test
	public void regiesterDuplicateNameKomand() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom1 = col.register(EchoKomand.class);
		IKomand kom2 = col.register(EchoDuplicateKomand.class);
		Assert.assertNull(kom2);
	}

	@Test
	public void regiester() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(EchoKomand.class);
		Assert.assertNotNull(kom);
	}

	@Test
	public void registered() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(EchoKomand.class);
		boolean isRegistered = col.registered("echo");
		Assert.assertTrue(isRegistered);
	}

	@Test
	public void notRegistered() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(EchoKomand.class);
		boolean isRegistered = col.registered("hello");
		Assert.assertFalse(isRegistered);
	}

	@Test
	public void getKomandByName() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(EchoKomand.class);
		IKomand kom2 = col.getKomandByName("echo");
		Assert.assertEquals(kom, kom2);
	}

	@Test
	public void getKomandAt() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(EchoKomand.class);
		IKomand kom2 = col.getKomandAt(0);
		Assert.assertEquals(kom, kom2);
	}

	@Test
	public void numCommand() throws Exception {
		KomandCollection col = new KomandCollection();
		IKomand kom = col.register(EchoKomand.class);
		Assert.assertEquals(1, col.size());
	}

}