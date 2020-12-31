package classification.examples.sms;

import classification.data.BinaryLabel;
import classification.data.SupervisedSample;

import classification.io.SampleParser;

/**
 * Kann verwendet werden, um ein beschriftetes {@link Message}-Objekt zu
 * erzeugen.
 * Das Label muss hierfür in den an {@link #parse(String[])} übergebenen
 * Parametern an erster Stelle stehen und im Falle von Positivbeispielen den
 * Wert {@value #POSITIVE_CLASS_NAME} haben.
 * An zweiter Stelle steht der Inhalt der Nachricht.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class LabeledMessageParser
    implements SampleParser<SupervisedSample<Message, BinaryLabel>> {
    
    private static final String POSITIVE_CLASS_NAME = "spam";

    @Override
    public SupervisedSample<Message, BinaryLabel> parse(String[] values) {
        var message = new Message(values[1]);

        var label = values[0].equals(POSITIVE_CLASS_NAME)
            ? BinaryLabel.POSITIVE
            : BinaryLabel.NEGATIVE;

        return new SupervisedSample<>(message, label);
    }
}
