package classification.examples.twodimensional;

import classification.data.Sample;
import classification.linalg.Vector;

/**
 * Repräsentiert einen allgemeinen zweidimensionalen Datenpunkt mit x- und
 * y-Kompomente.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class Point implements Sample<Point> {
    private double x;
    private double y;

    /**
     * Erzeugt ein {@code Point}-Objekt mit den übergebenen x- und y-Werten.
     *
     * @param x die x-Komponente des Punktes
     * @param y die y-Komponente des Punktes
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Vector getFeatures() {
        return new Vector(x, y);
    }
}
