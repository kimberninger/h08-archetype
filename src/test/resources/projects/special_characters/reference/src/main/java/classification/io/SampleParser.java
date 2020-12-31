package classification.io;

import classification.data.Sample;

/**
 * Erzeugt einen Datenpunkt aus einer Sequenz von {@code String}-Werten, die
 * zuvor aus einer Zeile in einer CSV-Datei erzeugt wurde.
 *
 * @author Kim Berninger
 * @version 1.1.0
 * 
 * @param <S> der Subtyp von {@link Sample}, dem die Datenpunkte, die dieser
 *            {@code SampleParser} liefert, entsprechen sollen
 */
public interface SampleParser<S extends Sample<?>> {
    /**
     * Erzeugt ein {@link Sample} aus einem {@code String}-Array.
     *
     * @param values die Werte, die aus der CSV-Datei geladen wurden
     * @return der Datenpunkt, der
     */
    S parse(String[] values);
}
