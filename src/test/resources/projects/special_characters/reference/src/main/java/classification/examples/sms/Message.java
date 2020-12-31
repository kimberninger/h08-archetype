package classification.examples.sms;

import classification.data.Sample;
import classification.linalg.Vector;

/**
 * Repräsentiert eine Chatnachricht, die als Datenpunkt für NLP-Verfahren
 * verwendet werden kann.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class Message implements Sample<Message> {
    private String content;

    private Vector features;

    /**
     * Erzeugt ein {@code Message}-Objekt mit den übergebenen {@code String} als
     * Inhalt der Nachricht.
     *
     * @param content der Inhalt der Nachricht
     */
    public Message(String content) {
        this.content = content;
    }

    /**
     * Liefert den Inhalt der Nachricht.
     * @return der Inhalt der Nachricht
     */
    public String getContent() {
        return content;
    }

    /**
     * Berechnet für diese Nachricht den Feature-Vektor mit Hilfe des
     * übergebenen {@code MessageVectorizer}-Objekts.
     * @param vectorizer der {@code MessageVectorizer}, der den Feature-Vektor
     *                   dieser Nachricht berechnen soll
     */
    public void computeFeatures(MessageVectorizer vectorizer) {
        features = vectorizer.vectorize(this);
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalStateException falls der Feature-Vektor noch nicht durch
     *                               die Methode
     *                               {@link #computeFeatures(MessageVectorizer)}
     *                               berechnet wurde
     *
     * @see #computeFeatures(MessageVectorizer)
     */
    @Override
    public Vector getFeatures() {
        if (features == null) {
            throw new IllegalStateException(
                "Der Feature-Vektor der Nachricht wurde noch nicht berechnet");
        }
        return features;
    }
}
