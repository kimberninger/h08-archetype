package classification.io;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import classification.data.Dataset;
import classification.data.Sample;

/**
 * Wird verwendet, um einen Datensatz aus einer CSV-Datei zu lesen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Subtyp von {@link Sample}, dem die Elemente des erzeugten
 *            Datensatzes entsprechen sollen
 */
public class CSVDatasetLoader<S extends Sample<?>> implements DatasetLoader<S> {
    private File csvFile;
    private CSVReader reader;
    private SampleParser<? extends S> parser;

    /**
     * Erzeugt einen {@code CSVDatasetLoader}, der einen Datzensatz aus der
     * gegebenen CSV-Datei lesen kann.
     *
     * @param csvFile   die zu lesende CSV-Datei
     * @param separator der Ausdruck, der in der CSV-Datei als Trennzeichen
     *                  verwendet wird
     * @param parser    der Parser, der verwendet werden soll, um eine Zeile der
     *                  CSV-Datei in den zugehörigen Datenpunkt umzuwandeln
     *
     * @see SampleParser#parse(String[])
     */
    public CSVDatasetLoader(File csvFile, String separator,
        SampleParser<? extends S> parser) {
        this.csvFile = csvFile;
        this.reader = new CSVReader(separator);
        this.parser = parser;
    }

    /**
     * Erzeugt einen {@code CSVDatasetLoader}, der einen Datzensatz aus der
     * gegebenen CSV-Datei lesen kann.
     * Das verwendete Trennzeichen entspricht dem Standard-Trennzeichen, das in
     * {@link CSVReader#CSVReader()} gesetzt wird.
     *
     * @param csvFile die zu lesende CSV-Datei
     * @param parser  der Parser, der verwendet werden soll, um eine Zeile der
     *                CSV-Datei in den zugehörigen Datenpunkt umzuwandeln
     *
     * @see CSVReader#CSVReader()
     */
    public CSVDatasetLoader(File csvFile, SampleParser<? extends S> parser) {
        this.csvFile = csvFile;
        this.reader = new CSVReader();
        this.parser = parser;
    }

    @Override
    public Dataset<S> loadDataset() {
        try {
            @SuppressWarnings("unchecked")
            Dataset<S> samples = Class
                .forName("classification.data.ListDataset")
                .asSubclass(Dataset.class).getConstructor().newInstance();

            for (var line : reader.readFile(csvFile)) {
                samples.add(parser.parse(line));
            }

            return samples;
        } catch (ReflectiveOperationException e) {
            System.err
                .println("ListDataset-Objekt konnte nicht erzeugt werden");
            return null;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
