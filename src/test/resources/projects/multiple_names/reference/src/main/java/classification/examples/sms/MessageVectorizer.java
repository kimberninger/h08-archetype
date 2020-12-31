package classification.examples.sms;

import classification.linalg.Vector;

/**
 * Wird verwendet, um den Feature-Vektor eines {@code Message}-Objekts zu
 * berechnen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @see Message
 * @see Message#getFeatures()
 */
public interface MessageVectorizer {
    /**
     * Berechnet den Feature-Vektor für das übergebene {@code Message}-Objekt.
     *
     * @param message die Nachricht, für die der Feature-Vektor berechnet werden
     *                soll
     *
     * @return den Feature-Vektor der Nachricht
     */
    Vector vectorize(Message message);
}
