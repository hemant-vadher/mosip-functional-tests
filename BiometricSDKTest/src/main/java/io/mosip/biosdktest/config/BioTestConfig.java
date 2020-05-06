package io.mosip.biosdktest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.mosip.kernel.core.bioapi.exception.BiometricException;
import io.mosip.kernel.core.bioapi.spi.IBioApi;

/**
 * @author Manoj SP
 *
 */
@Configuration
public class BioTestConfig {

	@Autowired
	private Environment env;
	
	@Bean("finger")
	public IBioApi fingerApi() throws BiometricException {
		try {
			System.err.println(env.getProperty("biotest.fingerprint.provider"));
			return (IBioApi) Class.forName(env.getProperty("biotest.fingerprint.provider")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new BiometricException("", "Unable to load fingerprint provider", e);
		}
	}
	
	@Bean("face")
	public IBioApi faceApi() throws BiometricException {
		try {
			System.err.println(env.getProperty("biotest.face.provider"));
			return (IBioApi) Class.forName(env.getProperty("biotest.face.provider")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new BiometricException("", "Unable to load face provider", e);
		}
	}
	
	@Bean("iris")
	public IBioApi irisApi() throws BiometricException {
		try {
			System.err.println(env.getProperty("biotest.iris.provider"));
			return (IBioApi) Class.forName(env.getProperty("biotest.iris.provider")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new BiometricException("", "Unable to load iris provider", e);
		}
	}
}
