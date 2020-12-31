package classification.io;

import classification.data.Dataset;
import classification.data.Sample;

/**
 * Fabrik-Interface zur Erstellung von {@code Dataset}-Objekten.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Subtyp von {@link Sample}, dem die Elemente des erzeugten
 *            Datensatzes entsprechen sollen
 *
 * @see Dataset
 * @see Dataset#load(DatasetLoader)
 */
public interface DatasetLoader<S extends Sample<?>> {
    /**
     * Erzeugt ein {@code Dataset}-Objekt.
     * @return der erzeugte Datensatz
     */
    Dataset<S> loadDataset();
}
