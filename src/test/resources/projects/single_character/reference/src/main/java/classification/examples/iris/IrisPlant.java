package classification.examples.iris;

import classification.data.Sample;
import classification.linalg.Vector;

/**
 * Repräsentiert eine Schwertlilie, die als Datenpunkt verwendet werden kann.
 * Ein solcher zeichnet sich durch die Höhe und Breite der Kelch- und
 * Kronblätter der Pflanze aus.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class IrisPlant implements Sample<IrisPlant> {
    private double sepalLength;
    private double sepalWidth;
    private double petalLength;
    private double petalWidth;

    /**
     * Erzeugt ein {@code IrisPlant}-Objekt mit den übergebenen Parametern.
     *
     * @param sepalLength die Länge der Kelchblätter
     * @param sepalWidth die Breite der Kelchblätter
     * @param petalLength die Länge der Kronblätter
     * @param petalWidth die Breite der Kronblätter
     */
    public IrisPlant(double sepalLength, double sepalWidth, double petalLength,
        double petalWidth) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
    }

    @Override
    public Vector getFeatures() {
        return new Vector(sepalLength, sepalWidth, petalLength, petalWidth);
    }
}
