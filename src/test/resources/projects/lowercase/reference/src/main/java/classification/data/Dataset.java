package classification.data;

import classification.io.DatasetLoader;

/**
 * Ein allgemeiner Datensatz zum Trainieren und Evaluieren von
 * Machine-Learning-Modellen.
 * Die Elemente, sog. Datenpunkte, eines Datensatzes können sowohl unbeschriftet
 * ({@link Sample}) als auch beschriftet ({@link SupervisedSample}) sein.
 * <br>
 * Als Subinterface von {@link Iterable} lässt sich über die in einem
 * {@code Dataset}-Objekt enthaltenden Datenpunkte mit einer foreach-Schleife
 * iterieren:
 * <blockquote>
 *   <pre><code>
 *for (var sample : dataset) {
 *    var phi = sample.getFeatures();
 *    ...
 *}
 *   </code></pre>
 * </blockquote>
 * Eine Implementation dieser Klasse sollte die Datenpunkte in einer geordneten
 * Datenstruktur verwalten, damit die Iterationsreihenfolge deterministisch ist.
 * Lediglich der Aufruf von {@link #shuffle()} sollte dafür sorgen, dass sich
 * diese Reihenfolge ändern kann. 
 * <br>
 * Die Klassenmethode {@link #load(DatasetLoader)} dient der Erstellung eines
 * Datensatzes aus einer einer externen Datenquelle, wie zum Beispiel einer
 * CSV-Datei.
 * 
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Subtyp von {@link Sample}, dem die Elemente dieses Datensatzes
 *            entsprechen sollen
 * 
 * @see Sample
 * @see SupervisedSample
 * @see classification.io.DatasetLoader
 */
public interface Dataset<S extends Sample<?>> extends Iterable<S> {
    /**
     * Fügt einen Datenpunkt zu diesem Datensatz hinzu.
     * Eine Implementation dieser Methode sollte dafür sorgen, dass sich der
     * neue Datenpunkt nach ihrem Aufruf am Ende der zugrunde liegenden
     * Datenstruktur befindet.
     * 
     * @param sample der hinzuzufügende Datenpunkt
     */
    void add(S sample);

    /**
     * Ordnet die Datenpunkte innerhalb dieses Datensatzes in einer zufälligen
     * Reihenfolge neu an.
     */
    void shuffle();

    /**
     * Lädt einen Datensatz.
     * @param <S>    der Subtyp von {@link Sample}, dem die Elemente des
     *               resultierenden Datensatzes entsprechen sollen
     * @param loader der {@link DatasetLoader}, der benutzt werden soll, um den
     *               externen Datensatz zu erzeugen
     *
     * @return den geladenen Datensatz
     */
    static <S extends Sample<?>> Dataset<S> load(DatasetLoader<S> loader) {
        return loader.loadDataset();
    }
}
