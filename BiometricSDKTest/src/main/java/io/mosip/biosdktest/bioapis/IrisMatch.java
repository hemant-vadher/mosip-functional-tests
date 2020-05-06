package io.mosip.biosdktest.bioapis;

import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.mosip.kernel.cbeffutil.impl.CbeffImpl;
import io.mosip.kernel.core.bioapi.model.CompositeScore;
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
public class IrisMatch {

	@Autowired
	@Qualifier("iris")
	private IBioApi iris;

	private CbeffUtil cbeffReader = new CbeffImpl();

	public void test() throws Exception {
		byte[] probe = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_iris_probe.xml"));
		byte[] probeFail = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_iris_probe2.xml"));
		byte[] gallery = IOUtils
				.toByteArray(this.getClass().getClassLoader().getResourceAsStream("cbeff_iris_gallery.xml"));

		BIR probe1 = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(probe, SingleType.IRIS.value()))
				.get(1);
		BIR probe2 = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(probe, SingleType.IRIS.value()))
				.get(0);
		BIR probe1Fail = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(probeFail, SingleType.IRIS.value()))
				.get(0);
		BIR probe2Fail = cbeffReader.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(probeFail, SingleType.IRIS.value()))
				.get(1);
		BIR gallery1 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(gallery, SingleType.IRIS.value())).get(1);
		BIR gallery2 = cbeffReader
				.convertBIRTypeToBIR(cbeffReader.getBIRDataFromXMLType(gallery, SingleType.IRIS.value())).get(0);

		System.err.println();
		System.err.println();

		System.err.println("Quality check:");
		System.err.println();
		System.err.println();
		System.err.println("probe1 - " + iris.checkQuality(probe1, new KeyValuePair[2]).getInternalScore());
		System.err.println("probe2 - " + iris.checkQuality(probe2, new KeyValuePair[2]).getInternalScore());
		System.err.println("probe1Fail - " + iris.checkQuality(probe1Fail, new KeyValuePair[2]).getInternalScore());
		System.err.println("probe2Fail - " + iris.checkQuality(probe2Fail, new KeyValuePair[2]).getInternalScore());
		System.err.println("gallery1 - " + iris.checkQuality(gallery1, new KeyValuePair[2]).getInternalScore());
		System.err.println("gallery2 - " + iris.checkQuality(gallery2, new KeyValuePair[2]).getInternalScore());

		System.err.println();
		System.err.println();

		System.err.println("match:");
		System.err.println();
		System.err.println();

		Score[] match1 = iris.match(probe1, new BIR[] { gallery1, gallery2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe1 - probe1 - " + match1[0].getInternalScore());
		System.err.println("getScaleScore -> probe1 - probe1 - " + match1[0].getScaleScore());
		System.err.println();

		Score[] match3 = iris.match(probe2, new BIR[] { probe2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe2 - probe2 - " + match3[0].getInternalScore());
		System.err.println("getScaleScore -> probe2 - probe2 - " + match3[0].getScaleScore());
		System.err.println();

		Score[] match7 = iris.match(probe1, new BIR[] { probe2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe1 - probe2 - " + match7[0].getInternalScore());
		System.err.println("getScaleScore -> probe1 - probe2 - " + match7[0].getScaleScore());
		System.err.println();

		Score[] match5 = iris.match(probe2, new BIR[] { probe1 }, new KeyValuePair[2]);
		System.err.println("internal -> probe2 - probe1 - " + match5[0].getInternalScore());
		System.err.println("getScaleScore -> probe2 - probe1 - " + match5[0].getScaleScore());
		System.err.println();

		Score[] match2 = iris.match(probe1, new BIR[] { gallery1 }, new KeyValuePair[2]);
		System.err.println("internal -> probe1 - gallery1 - " + match2[0].getInternalScore());
		System.err.println("getScaleScore -> probe1 - gallery1 - " + match2[0].getScaleScore());
		System.err.println();

		Score[] match8 = iris.match(probe1, new BIR[] { gallery2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe1 - gallery2 - " + match8[0].getInternalScore());
		System.err.println("getScaleScore -> probe1 - gallery2 - " + match8[0].getScaleScore());
		System.err.println();

		Score[] match4 = iris.match(probe2, new BIR[] { gallery1 }, new KeyValuePair[2]);
		System.err.println("internal -> probe2 - gallery1 - " + match4[0].getInternalScore());
		System.err.println("getScaleScore -> probe2 - gallery1 - " + match4[0].getScaleScore());
		System.err.println();

		Score[] match6 = iris.match(probe2, new BIR[] { gallery2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe2 - gallery1 - " + match6[0].getInternalScore());
		System.err.println("getScaleScore -> probe2 - gallery1 - " + match6[0].getScaleScore());
		System.err.println();

		Score[] match9 = iris.match(probe1, new BIR[] { gallery1, gallery2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe1 - gallery1, gallery2 - " + match9[0].getInternalScore());
		System.err.println("getScaleScore -> probe1 - gallery1, gallery2 - " + Stream.of(match9).mapToDouble(Score::getScaleScore).max().orElse(0));
		System.err.println();

		Score[] match10 = iris.match(probe2, new BIR[] { gallery1, gallery2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe2 - gallery1, gallery2 - " + match10[0].getInternalScore());
		System.err.println("getScaleScore -> probe2 - gallery1, gallery2 - " + Stream.of(match10).mapToDouble(Score::getScaleScore).max().orElse(0));
		System.err.println();
		
		Score[] match12 = iris.match(probe1Fail, new BIR[] { gallery1, gallery2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe1Fail - gallery1, gallery2 - " + match12[0].getInternalScore());
		System.err.println("getScaleScore -> probe1Fail - gallery1, gallery2 - " + match12[0].getScaleScore());
		System.err.println();
		
		Score[] match13 = iris.match(probe2Fail, new BIR[] { probe1, probe2 }, new KeyValuePair[2]);
		System.err.println("internal -> probe2Fail - probe1, probe2 - " + match13[0].getInternalScore());
		System.err.println("getScaleScore -> probe2Fail - probe1, probe2 - " + match13[0].getScaleScore());
		System.err.println();

		System.err.println();
		System.err.println("composite score:");
		System.err.println();
		System.err.println();

		CompositeScore match11 = iris.compositeMatch(new BIR[] { probe1, probe2 }, new BIR[] { gallery1, gallery2 }, null);
		System.err.println("internal -> probe1, probe2 - gallery1, gallery2 - " + match11.getInternalScore());
		System.err.println("getScaledScore -> probe1, probe2 - gallery1, gallery2 - " + match11.getScaledScore());
		System.err.println();

	}
}
