package classification.examples.twodimensional;

import classification.io.SampleParser;

/**
 * Kann verwendet werden, um ein unbeschriftetes {@link Point}-Objekt zu
 * erzeugen.
 * An erster und zweiter Stelle stehen die x- bzw. y-Werte, die sich als
 * {@code double} parsen lassen sollen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class PointParser implements SampleParser<Point> {
    @Override
    public Point parse(String[] values) {
        var x = Double.parseDouble(values[0]);
        var y = Double.parseDouble(values[1]);
        return new Point(x, y);
    }
}
