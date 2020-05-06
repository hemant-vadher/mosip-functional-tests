package io.mosip.biosdktest.bioapis;

import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.mosip.kernel.cbeffutil.impl.CbeffImpl;
import io.mosip.kernel.core.bioapi.model.KeyValuePair;
import io.mosip.kernel.core.bioapi.model.Score;
import io.mosip.kernel.core.bioapi.spi.IBioApi;
import io.mosip.kernel.core.cbeffutil.entity.BIR;
import io.mosip.kernel.core.cbeffutil.jaxbclasses.SingleType;
import io.mosip.kernel.core.cbeffutil.spi.CbeffUtil;

/**
 * @author Manoj SP
 *
 */
@Component
public class FaceMatch {

	@Autowired
	@Qualifier("face")
	private IBioApi face;

	private CbeffUtil cbeffReader = new CbeffImpl();

	public void test() throws Exception {
		byte[] gallery = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_face_gallery.xml"));
		byte[] probe = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_face_probe.xml"));
		byte[] probeFail = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_face_probe2.xml"));

		BIR galleryFace = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(gallery, SingleType.FACE.value())).get(0);
		BIR probeFace = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(probe, SingleType.FACE.value())).get(0);
		BIR probeFaceFail = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(probeFail, SingleType.FACE.value())).get(0);

		System.err.println("\n\nFace Quality Check: \n");
		System.err.println("galleryFace  " + face.checkQuality(galleryFace, new KeyValuePair[2]).getInternalScore());
		System.err.println();
		System.err.println();
		System.err.println("probeFace  " + face.checkQuality(probeFace, new KeyValuePair[2]).getInternalScore());
		System.err.println();
		System.err.println();
		System.err.println("probeFaceFail  " + face.checkQuality(probeFaceFail, new KeyValuePair[2]).getInternalScore());
		System.err.println();
		System.err.println();
		System.err.println("\nFace Match:\n");
		Score[] match = face.match(probeFace, new BIR[] { galleryFace }, new KeyValuePair[2]);
		System.err.println();
		System.err.println("Match score size: " + match.length);
		System.err.println();
		Arrays.asList(match).forEach(score -> {
			System.err.println("internalScore -> probeFace -> galleryFace  " + score.getInternalScore());
			System.err.println("scaledScore -> probeFace -> galleryFace  " + score.getScaleScore());
		});
		System.err.println("\nFace Match non matching:\n");
		Score[] matchFail = face.match(probeFaceFail, new BIR[] { galleryFace }, new KeyValuePair[2]);
		System.err.println();
		System.err.println("Match score size: " + match.length);
		System.err.println();
		Arrays.asList(matchFail).forEach(score -> {
			System.err.println("internalScore -> probeFace -> galleryFace  " + score.getInternalScore());
			System.err.println("scaledScore -> probeFace -> galleryFace  " + score.getScaleScore());
		});
	}

	public void test1() throws Exception {

		byte[] cbeff1 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_bala1.xml"));
		byte[] cbeff2 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_bala_sideview1.xml"));
		byte[] cbeff3 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_bala_sideview2.xml"));
		byte[] cbeff4 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_bala_sideview3.xml"));
		// byte[] cbeff5 = IOUtils.toByteArray(
		// this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_bala2.xml"));
		byte[] cbeff6 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_bala_specs.xml"));
		byte[] cbeff7 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_leona1.xml"));
		byte[] cbeff8 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_multi_face.xml"));
		byte[] cbeff9 = IOUtils.toByteArray(
				this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_saravana1.xml"));
		// byte[] cbeff10 = IOUtils.toByteArray(
		// this.getClass().getClassLoader().getResourceAsStream("cbeff_Sample_by_reg_client_without_face.xml"));

		BIR face1 = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff1, SingleType.FACE.value()))
				.get(0);

		BIR face1side1 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff2, SingleType.FACE.value())).get(0);

		BIR face1side2 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff3, SingleType.FACE.value())).get(0);

		BIR face1side3 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff4, SingleType.FACE.value())).get(0);
		// BIR face1side4 =
		// cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff5,
		// SingleType.FACE.value()))
		// .get(0);
		BIR face1specs = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff6, SingleType.FACE.value())).get(0);
		BIR face2 = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff7, SingleType.FACE.value()))
				.get(0);
		BIR multiface = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff8, SingleType.FACE.value())).get(0);
		BIR face3 = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff9, SingleType.FACE.value()))
				.get(0);
		// BIR withoutface =
		// cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(cbeff10,
		// SingleType.FACE.value()))
		// .get(0);

		System.err.println("\nQuality Check: ");
		System.err.println("face1  " + face.checkQuality(face1, new KeyValuePair[2]).getInternalScore());
		System.err.println("face1side1  " + face.checkQuality(face1side1, new KeyValuePair[2]).getInternalScore());
		System.err.println("face1side2  " + face.checkQuality(face1side2, new KeyValuePair[2]).getInternalScore());
		System.err.println("face1side3  " + face.checkQuality(face1side3, new KeyValuePair[2]).getInternalScore());
		// System.err.println("face1side4 " + face.checkQuality(face1side4, new
		// KeyValuePair[2]).getInternalScore());
		System.err.println("face1specs  " + face.checkQuality(face1specs, new KeyValuePair[2]).getInternalScore());
		System.err.println("face2  " + face.checkQuality(face2, new KeyValuePair[2]).getInternalScore());
		System.err.println("multiface  " + face.checkQuality(multiface, new KeyValuePair[2]).getInternalScore());
		System.err.println("face3  " + face.checkQuality(face3, new KeyValuePair[2]).getInternalScore());
		// System.err.println("withoutface " + face.checkQuality(withoutface, new
		// KeyValuePair[2]).getInternalScore());

		System.err.println("\nMatch score scaled:");
		System.err.println(
				"face1 -> face1  " + face.match(face1, new BIR[] { face1 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println(
				"face1 -> face2  " + face.match(face1, new BIR[] { face2 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println(
				"face1 -> face3  " + face.match(face1, new BIR[] { face3 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> multiface  "
				+ face.match(face1, new BIR[] { multiface }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> face1side1  "
				+ face.match(face1, new BIR[] { face1side1 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> face1side2  "
				+ face.match(face1, new BIR[] { face1side2 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> face1side3  "
				+ face.match(face1, new BIR[] { face1side3 }, new KeyValuePair[2])[0].getScaleScore());
		// System.err.println("face1 -> face1side4 " + face.match(face1, new BIR[] {
		// face1side4 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> face1specs  "
				+ face.match(face1, new BIR[] { face1specs }, new KeyValuePair[2])[0].getScaleScore());
		// System.err.println("face1 -> withoutface " + face.match(face1, new BIR[] {
		// withoutface }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> face1side1, face1side2, face1side3, face1side4, face1specs  " + face.match(face1,
				new BIR[] { face1specs, face1side1, face1side2, face1side3 }, new KeyValuePair[2])[0].getScaleScore());
		System.err.println("face1 -> face1side1, face1side2, face1side3, face1side4  "
				+ face.match(face1, new BIR[] { face1side1, face1side2, face1side3 }, new KeyValuePair[2])[0]
						.getScaleScore());

		System.err.println("\nMatch score internal:");
		System.err.println(
				"face1 -> face1  " + face.match(face1, new BIR[] { face1 }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println(
				"face1 -> face2  " + face.match(face1, new BIR[] { face2 }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println(
				"face1 -> face3  " + face.match(face1, new BIR[] { face3 }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> multiface  "
				+ face.match(face1, new BIR[] { multiface }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> face1side1  "
				+ face.match(face1, new BIR[] { face1side1 }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> face1side2  "
				+ face.match(face1, new BIR[] { face1side2 }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> face1side3  "
				+ face.match(face1, new BIR[] { face1side3 }, new KeyValuePair[2])[0].getInternalScore());
		// System.err.println("face1 -> face1side4 " + face.match(face1, new BIR[] {
		// face1side4 }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> face1specs  "
				+ face.match(face1, new BIR[] { face1specs }, new KeyValuePair[2])[0].getInternalScore());
		// System.err.println("face1 -> withoutface " + face.match(face1, new BIR[] {
		// withoutface }, new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> face1side1, face1side2, face1side3, face1side4, face1specs  "
				+ face.match(face1, new BIR[] { face1specs, face1side1, face1side2, face1side3 },
						new KeyValuePair[2])[0].getInternalScore());
		System.err.println("face1 -> face1side1, face1side2, face1side3, face1side4  "
				+ face.match(face1, new BIR[] { face1side1, face1side2, face1side3 }, new KeyValuePair[2])[0]
						.getInternalScore());
	}

}
