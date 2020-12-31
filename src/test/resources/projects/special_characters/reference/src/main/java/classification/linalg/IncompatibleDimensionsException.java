package classification.linalg;

/**
 * Wird geworfen, wenn eine Vektor-Operation nicht ausgeführt werden kann, weil
 * die Dimensionen der beiden Operanden nicht kompatibel sind. 
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class IncompatibleDimensionsException extends RuntimeException {
    private static final long serialVersionUID = 4697023914971151046L;

    /**
     * Erzeugt eine {@code IncompatibleDimensionsException} mit den beiden
     * Dimensionen, die nicht kompatibel sind.
     *
     * @param dim1 die Dimension des ersten Operanden
     * @param dim2 die Dimension des zweiten Operanden
     */
    public IncompatibleDimensionsException(int dim1, int dim2) {
        super(String.format("Inkompatible Dimensionen: %d und %d", dim1, dim2));
    }
}
