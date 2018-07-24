package org.kolonitsky.komander.samples;

import org.kolonitsky.komander.BaseKomand;

/**
 * Created by akalanitski on 22.07.2018.
 */
public class EchoKomand extends BaseKomand {

	public EchoKomand() {
		_name = "echo";
		_category = "test";
		_shortDescription = "print it's input to output";
	}

	@Override
	public void run(String[] arguments) throws Exception {
		_output = _name + " " + _input.rawInputLine();
	}
}
