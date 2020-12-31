package classification.examples.iris;

import classification.io.SampleParser;

/**
 * Kann verwendet werden, um ein unbeschriftetes {@link IrisPlant}-Objekt aus
 * einer Sequenz von Kelchblatt-Länge und -Breite sowie Kronblatt-Länge und
 * -Breite zu erzeugen.
 * Die vier Parameter sollten sich hierfür als {@code double}-Werte parsen
 * lassen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class PlantParser implements SampleParser<IrisPlant> {
    @Override
    public IrisPlant parse(String[] values) {
        var sepalLength = Double.parseDouble(values[0]);
        var sepalWidth = Double.parseDouble(values[1]);
        var petalLength = Double.parseDouble(values[2]);
        var petalWidth = Double.parseDouble(values[3]);

        return new IrisPlant(sepalLength, sepalWidth, petalLength, petalWidth);
    }
}
