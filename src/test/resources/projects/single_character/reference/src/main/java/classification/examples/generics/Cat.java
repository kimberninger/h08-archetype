package classification.examples.generics;

import classification.data.Sample;
import classification.linalg.Vector;

/**
 * @author Kim Berninger
 * @version 1.1.0
 */
class Cat implements Sample<Animal> {
    @Override
    public Vector getFeatures() {
        return null;
    }
}
