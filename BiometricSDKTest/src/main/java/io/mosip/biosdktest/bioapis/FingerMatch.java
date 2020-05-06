package io.mosip.biosdktest.bioapis;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.mosip.kernel.cbeffutil.impl.CbeffImpl;
import io.mosip.kernel.core.bioapi.exception.BiometricException;
import io.mosip.kernel.core.bioapi.model.QualityScore;
import io.mosip.kernel.core.bioapi.model.Score;
import io.mosip.kernel.core.bioapi.spi.IBioApi;
import io.mosip.kernel.core.cbeffutil.entity.BIR;
import io.mosip.kernel.core.cbeffutil.jaxbclasses.SingleType;

/**
 * @author Manoj SP
 *
 */
@Component
public class FingerMatch {

	public static void main(String[] args) throws Exception {
		FingerMatch match = new FingerMatch();
		match.test();
	}

	@Autowired
	@Qualifier("finger")
	private IBioApi finger;

	private CbeffImpl cbeffReader = new CbeffImpl();

	public void test() throws Exception {
		byte[] cbeffFingerSingle = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_finger_single.xml"));
		byte[] cbeffFingerProbeFail = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_finger_probe2.xml"));
		byte[] cbeffFingerSlab = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_finger_slab.xml"));
		List<BIR> singleBirList = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeffFingerSingle, SingleType.FINGER.value()));
		List<BIR> singleBirFailList = cbeffReader.convertBIRTypeToBIR(
				cbeffReader.getBIRDataFromXMLType(cbeffFingerProbeFail, SingleType.FINGER.value()));
		List<BIR> slabBirList = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeffFingerSlab, SingleType.FINGER.value()));
		System.err.println();
		System.err.println();
		System.err.println("Single device FIR quality check:");
		singleBirList.forEach(bir -> {
			try {
				QualityScore score = finger.checkQuality(bir, null);
				System.err.println("finger quality of cbeff data  " + bir.getBdbInfo().getSubtype() + ": "
						+ score.getInternalScore());
			} catch (BiometricException e) {
				e.printStackTrace();
			}
		});
		System.err.println("Single device probe2 FIR quality check:");
		singleBirFailList.forEach(bir -> {
			try {
				QualityScore score = finger.checkQuality(bir, null);
				System.err.println("finger quality of cbeff data  " + bir.getBdbInfo().getSubtype() + ": "
						+ score.getInternalScore());
			} catch (BiometricException e) {
				e.printStackTrace();
			}
		});
		System.err.println();
		System.err.println();
		System.err.println("Slab device FIR quality check:");
		slabBirList.forEach(bir -> {
			try {
				System.err.println("finger quality of cbeff data  " + bir.getBdbInfo().getSubtype() + ": "
						+ finger.checkQuality(slabBirList.get(0), null).getInternalScore());
			} catch (BiometricException e) {
				e.printStackTrace();
			}
		});

		System.err.println("\n\n\n\n");
		System.err.println("FIR matching single vs slab: ");
		BIR[] slabBirs = slabBirList.toArray(new BIR[] {});
		System.err.println("slab size : " + slabBirs.length);
		singleBirList.forEach(bir -> {
			try {
				Score[] match = finger.match(bir, slabBirs, null);
				System.err.println("Match : " + match);
				AtomicInteger atomicInteger = new AtomicInteger();
				Arrays.asList(match).forEach(score -> {
					System.err.println(bir.getBdbInfo().getSubtype() + " vs "
							+ slabBirs[atomicInteger.get()].getBdbInfo().getSubtype() + " : "
							+ score.getInternalScore());
					System.err.println(bir.getBdbInfo().getSubtype() + " vs "
							+ slabBirs[atomicInteger.getAndIncrement()].getBdbInfo().getSubtype() + " : "
							+ score.getScaleScore());
				});
				System.err.println("\n\n\n\n");
				System.err.println("Result : " + Stream.of(match).mapToDouble(Score::getScaleScore).max().orElse(0));
				System.err.println("\n\n\n\n");
			} catch (BiometricException e) {
				e.printStackTrace();
			}
		});

		System.err.println("\n\n\n\n");
		System.err.println("FIR matching single Fail vs slab: ");
		System.err.println("slab size : " + slabBirs.length);
		singleBirFailList.forEach(bir -> {
			try {
				Score[] match = finger.match(bir, slabBirs, null);
				System.err.println("Match : " + match);
				Arrays.asList(match).forEach(score -> {
					System.err.println("finger match of device data of " + bir.getBdbInfo().getSubtype() + " : "
							+ score.getInternalScore());
					System.err.println("scaled finger match of device data of " + bir.getBdbInfo().getSubtype() + " : "
							+ score.getScaleScore());
				});
				System.err.println("\n\n\n\n");
				System.err.println("Result : " + Stream.of(match).mapToDouble(Score::getScaleScore).max().orElse(0));
				System.err.println("\n\n\n\n");
			} catch (BiometricException e) {
				e.printStackTrace();
			}
		});
	}

}
