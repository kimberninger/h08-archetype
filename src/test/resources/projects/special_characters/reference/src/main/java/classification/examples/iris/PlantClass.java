package classification.examples.iris;

import classification.data.Label;

/**
 * Beschreibt die Art einer Schwertlilie.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public enum PlantClass implements Label {
    /** Borsten-Schwertlilie */
    SETOSA,

    /** Verschiedenfarbige Schwertlilie */
    VERSICOLOR,

    /** Iris virginica */
    VIRGINICA;

    @Override
    public String toString() {
        switch (this) {
            case SETOSA:
                return "Iris-setosa";
            case VERSICOLOR:
                return "Iris-versicolor";
            default:
                return "Iris-virginica";
        }
    }
}
