package classification.examples.twodimensional;

import java.util.Arrays;

import classification.data.BinaryLabel;
import classification.data.SupervisedSample;

import classification.io.SampleParser;

/**
 * Kann verwendet werden, um ein beschriftetes {@link Point}-Objekt zu
 * erzeugen.
 * Das Label muss hierfür in den an {@link #parse(String[])} übergebenen
 * Parametern an dritter Stelle stehen und im Falle von Positivbeispielen den
 * Wert {@value #POSITIVE_CLASS_NAME} haben.
 * An erster und zweiter Stelle stehen die x- bzw. y-Werte, die sich als
 * {@code double} parsen lassen sollen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class LabeledPointParser
    implements SampleParser<SupervisedSample<Point, BinaryLabel>> {
    private static final String POSITIVE_CLASS_NAME = "p";
    
    private PointParser parser = new PointParser();

    @Override
    public SupervisedSample<Point, BinaryLabel> parse(String[] values) {
        var point = parser.parse(Arrays.copyOfRange(values, 0, 2));

        var label = values[2].equals(POSITIVE_CLASS_NAME)
            ? BinaryLabel.POSITIVE
            : BinaryLabel.NEGATIVE;

        return new SupervisedSample<>(point, label);
    }
}
