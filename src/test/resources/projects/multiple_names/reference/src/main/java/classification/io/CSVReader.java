package classification.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.util.function.Predicate.not;

/**
 * Wird verwendet um CSV-Dateien einzulesen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class CSVReader {
    private static final String STANDARD_SEPARATOR = ",";

    private String separator;

    /**
     * Erzeugt einen neuen {@code CSVReader} mit {@value #STANDARD_SEPARATOR}
     * als Trennzeichen.
     */
    public CSVReader() {
        this(STANDARD_SEPARATOR);
    }

    /**
     * Erzeugt einen neuen {@code CSVReader} mit dem übergebenen String als
     * Trennzeichen.
     * Hierfür kann auch ein regulärer Ausdruck verwendet werden.
     *
     * @param separator der Ausdruck, an dem eine Zeile der CSV-Datei geteilt
     *                  werden soll
     */
    public CSVReader(String separator) {
        this.separator = separator;
    }

    /**
     * Verwendet diesen {@code CSVReader} um eine CSV-Datei einzulesen.
     * Leerzeilen werden hierbei ignoriert.
     *
     * @param csvFile die einzulesende CSV-Datei
     * @return ein zweidimensionales {@code String}-Array, wobei in Eintrag
     *         {@code [i][j]} das j-te Element der i-ten Zeile gespeichert ist
     * @throws IOException falls beim Öffnen der Datei ein Fehler auftritt 
     */
    public String[][] readFile(File csvFile) throws IOException {
        return Files.readAllLines(csvFile.toPath()).stream()
            .filter(not(String::isBlank))
            .map(line -> line.strip().split(separator))
            .toArray(String[][]::new);
    }
}
