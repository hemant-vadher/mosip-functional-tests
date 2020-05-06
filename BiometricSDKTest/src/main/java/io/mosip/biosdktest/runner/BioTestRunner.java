package io.mosip.biosdktest.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.mosip.biosdktest.bioapis.CompositeMatch;
import io.mosip.biosdktest.bioapis.FaceMatch;
import io.mosip.biosdktest.bioapis.FingerMatch;
import io.mosip.biosdktest.bioapis.IrisMatch;

/**
 * @author Manoj SP
 *
 */
@Component
public class BioTestRunner implements CommandLineRunner {
	
	@Autowired
	private FaceMatch face;
	
	@Autowired
	private IrisMatch iris;
	
	@Autowired
	private FingerMatch finger;
	
	@Autowired
	private CompositeMatch composite;

	@Override
	public void run(String... args) throws Exception {
		face.test();
		iris.test();
		finger.test();
		composite.test();
	}

}
