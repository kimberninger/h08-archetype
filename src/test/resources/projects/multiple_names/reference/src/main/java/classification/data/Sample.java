package classification.data;

import classification.linalg.Vector;

/**
 * Modelliert einen Datenpunkt mit einem Feature-Vektor, der durch
 * {@link #getFeatures} beschrieben wird.
 * <br>
 * Bei der Implementation dieses Interfaces ist darauf zu achten, dass für einen
 * festen Typ {@code S} alle Instanzen von {@code Sample<S>} ausschließlich
 * Feature-Vektoren derselben Dimension als Ergebnis von {@link #getFeatures}
 * geliefert werden.
 * Dasselbe soll auch für alle Instanzen von {@code Sample<T>} gelten, wobei
 * {@code T} ein beliebiger Untertyp von {@code S} ist.
 * 
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Typ, dessen Vektorrepräsentation durch {@link #getFeatures}
 *            beschrieben werden soll
 *
 * @see classification.linalg.Vector
 * @see Label
 * @see SupervisedSample
 */
public interface Sample<S> {
    /**
     * Liefert den Feature-Vektor dieses Datenpunktes.
     * 
     * @return den Feature-Vektor dieses Datenpunktes
     */
    Vector getFeatures();
}
