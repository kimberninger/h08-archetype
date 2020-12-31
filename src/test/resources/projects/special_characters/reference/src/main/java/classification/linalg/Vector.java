package classification.linalg;

import java.util.Arrays;
import java.util.Random;

/**
 * Repräsentiert einen endlich-dimensionalen reellen Vektor.
 * Die Komponenten eines solchen Vektors werden als {@code double}-Werte
 * gespeichert.
 * Instanzen dieser Klasse sind unveränderlich.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class Vector {
    private double[] data;

    private Vector(int dimension) {
        data = new double[dimension];
    }

    /**
     * Erzeugt einen neuen Vektor mit den angegebenen Komponenten.
     * Wird als Parameter ein Array übergeben, so werden den Komponenten dieses
     * Vektors Kopien der Array-Komponenten zugewiesen.
     * Änderungen am Array spiegeln sich daher nicht an diesem Vektor wieder.
     *
     * @param data die Komponenten, aus denen dieser Vektor bestehen soll
     */
    public Vector(double... data) {
        this.data = Arrays.copyOf(data, data.length);
    }

    /**
     * Liefert die Anzahl der Komponenten dieses Vektors.
     *
     * @return die Dimension dieses Vektors
     */
    public int getDimension() {
        return data.length;
    }

    /**
     * Erzeugt eine Kopie dieses Vektors mit derselben Dimension und denselben
     * Komponenten.
     *
     * @return eine Kopie dieses Vektors
     */
    public Vector copy() {
        return new Vector(data);
    }

    /**
     * Addiert den übergebenen Vektor auf diesen und liefert als Ergebnis die
     * Summe als neuen Vektor.
     *
     * @param other der zweite Summand
     *
     * @return die Summe der beiden Vektoren
     *
     * @throws IncompatibleDimensionsException wenn die Dimensionen der beiden
     *                                         Vektoren nicht übereinstimmen
     */
    public Vector add(Vector other) {
        if (getDimension() != other.getDimension()) {
            throw new IncompatibleDimensionsException(
                getDimension(), other.getDimension());
        }
        var result = copy();
        for (var i = 0; i < data.length; i++) {
            result.data[i] += other.data[i];
        }
        return result;
    }

    /**
     * Subtrahiert den übergebenen Vektor von diesem und liefert als Ergebnis
     * die Differenz als neuen Vektor.
     *
     * @param other der Subtrahend
     * @return die Differenz der beiden Vektoren
     * @throws IncompatibleDimensionsException wenn die Dimensionen der beiden
     *                                         Vektoren nicht übereinstimmen
     */
    public Vector sub(Vector other) {
        return add(other.mul(-1));
    }

    /**
     * Multipliziert jede Komponente dieses Vektors mit dem übergebenen Skalar
     * und liefert das Ergebnis als neuen Vektor zurück.
     *
     * @param factor der Skalar, mit dem der Vektor multipliziert werden soll
     * @return eine Kopie dieses Vektors mit skalierten Komponenten
     */
    public Vector mul(double factor) {
        var result = copy();
        for (var i = 0; i < data.length; i++) {
            result.data[i] *= factor;
        }
        return result;
    }

    /**
     * Dividiert jede Komponente dieses Vektors durch das übergebene Skalar und
     * liefert das Ergebnis als neuen Vektor zurück.
     *
     * @param factor der Skalar, durch das der Vektor dividiert werden soll
     *
     * @return eine Kopie dieses Vektors mit skalierten Komponenten
     */
    public Vector div(double factor) {
        return mul(1 / factor);
    }

    /**
     * Berechnet das Standardskalarprodukt des übergebenen Vektors und diesem.
     *
     * @param other der Vektor, mit dem dieser mulitpliziert werden soll
     *
     * @return das Skalarprodukt der beiden Vektoren
     *
     * @throws IncompatibleDimensionsException wenn die Dimensionen der beiden
     *                                         Vektoren nicht übereinstimmen
     */
    public double dot(Vector other) {
        if (getDimension() != other.getDimension()) {
            throw new IncompatibleDimensionsException(
                getDimension(), other.getDimension());
        }
        var result = 0.0;
        for (var i = 0; i < data.length; i++) {
            result += data[i] * other.data[i];
        }
        return result;
    }

    /**
     * Berechnet die Euklidische Norm (2-Norm) dieses Vektors.
     *
     * @return die Euklidische Norm dieses Vektors
     */
    public double norm() {
        return Math.sqrt(dot(this));
    }

    /**
     * Liefert den Nullvektor der entsprechenden Dimension.
     *
     * @param dimension die Dimension des zu erzeugenden Vektors
     *
     * @return den Nullvektor mit der übergebenen Dimension
     */
    public static Vector zeros(int dimension) {
        return new Vector(dimension);
    }

    /**
     * Initialisiert einen neuen Vektor mit zufälligen Werten aus der
     * Standardnormalverteilung.
     *
     * @param dimension die Dimension des zu erzeugenden Vektors
     *
     * @return einen zufälligen Vektor mit der übergebenen Dimension
     */
    public static Vector random(int dimension) {
        var random = new Random();
        var result = new Vector(dimension);
        for (var i = 0; i < dimension; i++) {
            result.data[i] = random.nextGaussian();
        }
        return result;
    }

    /**
     * Liefert den Nullvektor der entsprechenden Dimension.
     *
     * @param dimension die Dimension des zu erzeugenden Vektors
     *
     * @return den Nullvektor mit der übergebenen Dimension
     *
     * @deprecated ersetzt durch {@link #zeros(int)}
     */
    @Deprecated(since="1.1.0")
    public static Vector zero(int dimension) {
        return zeros(dimension);
    }
}
