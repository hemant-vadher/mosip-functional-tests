package io.mosip.biosdktest.bioapis;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.mosip.kernel.cbeffutil.impl.CbeffImpl;
import io.mosip.kernel.core.bioapi.exception.BiometricException;
import io.mosip.kernel.core.bioapi.model.CompositeScore;
import io.mosip.kernel.core.bioapi.spi.IBioApi;
import io.mosip.kernel.core.cbeffutil.entity.BIR;
import io.mosip.kernel.core.cbeffutil.jaxbclasses.SingleType;

/**
 * @author Manoj SP
 *
 */
@Component
public class CompositeMatch {

	@Autowired
	@Qualifier("finger")
	private IBioApi finger;

	private CbeffImpl cbeffReader = new CbeffImpl();

	public void test() throws Exception {
		byte[] cbeff_finger_single = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_finger_single.xml"));
		byte[] cbeff_finger_slab = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_finger_slab.xml"));
		List<BIR> singleBirList = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff_finger_single, SingleType.FINGER.value()));
		List<BIR> slabBirList = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff_finger_slab, SingleType.FINGER.value()));

		byte[] cbeffIris = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_iris_probe.xml"));
		BIR iris1 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeffIris, SingleType.IRIS.value())).get(1);
		BIR iris2 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeffIris, SingleType.IRIS.value())).get(0);
		slabBirList.add(iris1);
		slabBirList.add(iris2);

		byte[] cbeffFace = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_face_probe.xml"));
		BIR face = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeffFace, SingleType.FACE.value())).get(0);
		slabBirList.add(face);

		singleBirList.forEach(bir -> {
			try {
				CompositeScore match = finger.compositeMatch(new BIR[] { bir }, slabBirList.toArray(new BIR[] {}),
						null);
				System.err.println("internal -> " + bir.getBdbInfo().getSubtype()
						+ " -> all slab finger, iris, face -> " + match.getInternalScore());
				System.err.println("getScaledScore -> " + bir.getBdbInfo().getSubtype()
						+ " -> all slab finger, iris, face -> " + match.getScaledScore());
			} catch (BiometricException e) {
				e.printStackTrace();
			}
		});
		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchFinger = finger.compositeMatch(singleBirList.toArray(new BIR[] {}),
				slabBirList.toArray(new BIR[] {}), null);
		System.err.println(
				"internal -> all single finger -> all slab finger, iris, face -> " + matchFinger.getInternalScore());
		System.err.println("getScaledScore -> all single finger -> all slab finger, iris, face -> "
				+ matchFinger.getScaledScore());

		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchIris1 = finger.compositeMatch(new BIR[] { iris1 },
				slabBirList.toArray(new BIR[] {}), null);
		System.err.println(
				"internal -> iris1 -> all slab finger, iris, face -> " + matchIris1.getInternalScore());
		System.err.println(
				"getScaledScore -> iris1 -> all slab finger, iris, face -> " + matchIris1.getScaledScore());
		System.err.println();
		CompositeScore matchIris2 = finger.compositeMatch(new BIR[] { iris2 },
				slabBirList.toArray(new BIR[] {}), null);
		System.err.println(
				"internal -> iris2 -> all slab finger, iris, face -> " + matchIris2.getInternalScore());
		System.err.println(
				"getScaledScore -> iris2 -> all slab finger, iris, face -> " + matchIris2.getScaledScore());
		System.err.println();
		CompositeScore matchIris3 = finger.compositeMatch(new BIR[] { iris1, iris2 },
				slabBirList.toArray(new BIR[] {}), null);
		System.err.println(
				"internal -> iris1, iris2 -> all slab finger, iris, face -> " + matchIris3.getInternalScore());
		System.err.println(
				"getScaledScore -> iris1, iris2 -> all slab finger, iris, face -> " + matchIris3.getScaledScore());

		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchFace = finger.compositeMatch(new BIR[] { face }, slabBirList.toArray(new BIR[] {}), null);
		System.err.println("internal -> face -> all slab finger, iris, face -> " + matchFace.getInternalScore());
		System.err.println("getScaledScore -> face -> all slab finger, iris, face -> " + matchFace.getScaledScore());
		
		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchmix1 = finger.compositeMatch(new BIR[] { singleBirList.get(0), iris1, face }, slabBirList.toArray(new BIR[] {}), null);
		System.err.println("internal -> singleBirList.get(0), iris1, face -> all slab finger, iris, face -> " + matchmix1.getInternalScore());
		System.err.println("getScaledScore -> singleBirList.get(0), iris1, face -> all slab finger, iris, face -> " + matchmix1.getScaledScore());
		
		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchmix2 = finger.compositeMatch(new BIR[] { singleBirList.get(1), iris2, face }, slabBirList.toArray(new BIR[] {}), null);
		System.err.println("internal -> singleBirList.get(1), iris2, face -> all slab finger, iris, face -> " + matchmix2.getInternalScore());
		System.err.println("getScaledScore -> singleBirList.get(1), iris2, face -> all slab finger, iris, face -> " + matchmix2.getScaledScore());
		
		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchmix3 = finger.compositeMatch(new BIR[] { singleBirList.get(0), face }, slabBirList.toArray(new BIR[] {}), null);
		System.err.println("internal -> singleBirList.get(0), face -> all slab finger, iris, face -> " + matchmix3.getInternalScore());
		System.err.println("getScaledScore -> singleBirList.get(0), face -> all slab finger, iris, face -> " + matchmix3.getScaledScore());
		
		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchmix4 = finger.compositeMatch(new BIR[] { singleBirList.get(1), iris1 }, slabBirList.toArray(new BIR[] {}), null);
		System.err.println("internal -> singleBirList.get(1), iris1 -> all slab finger, iris, face -> " + matchmix4.getInternalScore());
		System.err.println("getScaledScore -> singleBirList.get(1), iris1 -> all slab finger, iris, face -> " + matchmix4.getScaledScore());
		
		System.err.println();
		System.err.println();
		System.err.println();
		CompositeScore matchmix5 = finger.compositeMatch(new BIR[] { iris2, face }, slabBirList.toArray(new BIR[] {}), null);
		System.err.println("internal -> iris2, face -> all slab finger, iris, face -> " + matchmix5.getInternalScore());
		System.err.println("getScaledScore -> iris2, face -> all slab finger, iris, face -> " + matchmix5.getScaledScore());
		
		try {
			System.err.println();
			System.err.println();
			System.err.println();
			CompositeScore matchwithoutfingeringallery = finger.compositeMatch(singleBirList.toArray(new BIR[] {}), new BIR[] {iris1, iris2, face}, null);
			System.err.println("internal -> all finger -> iris1, iris2, face -> " + matchwithoutfingeringallery.getInternalScore());
			System.err.println("getScaledScore -> all finger -> iris1, iris2, face -> " + matchwithoutfingeringallery.getScaledScore());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.err.println();
			System.err.println();
			System.err.println();
			slabBirList.remove(iris1);
			slabBirList.remove(iris2);
			CompositeScore matchwithoutIrisgallery = finger.compositeMatch(new BIR[] { iris1, iris2 }, slabBirList.toArray(new BIR[] {}), null);
			System.err.println("internal ->  iris1, iris2 -> all slab finger, face -> " + matchwithoutIrisgallery.getInternalScore());
			System.err.println("getScaledScore -> iris1, iris2 -> all slab finger, face -> " + matchwithoutIrisgallery.getScaledScore());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			System.err.println();
			System.err.println();
			System.err.println();
			slabBirList.add(iris1);
			slabBirList.add(iris2);
			slabBirList.remove(face);
			CompositeScore matchwithoutFaceGallery = finger.compositeMatch(new BIR[] { face }, slabBirList.toArray(new BIR[] {}), null);
			System.err.println("internal -> face -> all slab finger, iris -> " + matchwithoutFaceGallery.getInternalScore());
			System.err.println("getScaledScore -> face -> all slab finger, iris -> " + matchwithoutFaceGallery.getScaledScore());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
