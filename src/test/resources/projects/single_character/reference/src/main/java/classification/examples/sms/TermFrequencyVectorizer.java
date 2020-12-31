package classification.examples.sms;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeMap;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import java.util.stream.Collectors;

import classification.data.BinaryLabel;
import classification.data.Dataset;
import classification.data.SupervisedSample;
import classification.linalg.Vector;

import static java.util.function.Predicate.isEqual;

/**
 * Erzeugt Worthistogramme basierend auf einem festgelegten Vokabular.
 * Diese können als Feature-Vektoren für {@code Message}-Objekte verwendet
 * werden.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class TermFrequencyVectorizer implements MessageVectorizer {
    private List<String> vocabulary;

    private TermFrequencyVectorizer() {
        vocabulary = new ArrayList<>();
    }

    @Override
    public Vector vectorize(Message message) {
        var frequencies = vocabulary.stream()
            .mapToDouble(term -> termFrequency(term, message)).toArray();
        return new Vector(frequencies);
    }

    /**
     * Liefert die Größe des Vokabulars dieses {@code TermFrequencyVectorizer}s
     * und somit auch die Dimension der Feature-Vektoren, die von diesem erzeugt
     * werden.
     *
     * @return die Größe des Vokabulars
     */
    public int size() {
        return vocabulary.size();
    }

    private double termFrequency(String term, Message message) {
        return tokenize(message).stream().filter(isEqual(term)).count();
    }

    private static List<String> tokenize(Message message) {
        var pattern = Pattern.compile("[a-z][a-z0-9]*");
        var matcher = pattern.matcher(message.getContent().toLowerCase());
        return matcher.results().map(MatchResult::group)
            .collect(Collectors.toList());
    }

    /**
     * Erzeugt einen neuen {@code TermFrequencyVectorizer} und populiert dessen
     * Vokabular mit den Tokens, die in den Nachrichten des übergebenen
     * Datensatzes enthalten sind.
     * Das Vokabular beschränkt sich hierbei auf die {@code maxSize} Tokens mit
     * der höchsten Häufigkeit, wobei ein Token mindestens {@code minFreq}-mal
     * vorkommen muss.
     *
     * @param <T>     der Subtyp von {@link Message}, dem die Datenpunkte im
     *                übergebenen Datensatz entsprechen
     * @param data    der Datensatz, mit dessen Tokens das Vokabular des
     *                resultierenden {@code TermFrequencyVectorizer}s populiert
     *                werden soll
     * @param maxSize die maximale Vokabulargröße des resultierenden
     *                {@code TermFrequencyVectorizer}s
     * @param minFreq die minimale Häufigkeit eines Tokens, damit er in das
     *                Vokabular des resultierenden
     *                {@code TermFrequencyVectorizer}s aufgenommen wird
     *
     * @return ein {@code TermFrequencyVectorizer} mit dem entsprechend
     *         angelegten Vokabular
     */
    public static <T extends Message> TermFrequencyVectorizer fromDataset(
        Dataset<SupervisedSample<T, BinaryLabel>> data,
        int maxSize, int minFreq) {

        Map<String, Integer> vocabulary = new TreeMap<>();
        for (var sample : data) {
            for (var token : tokenize(sample.getSample())) {
                vocabulary.merge(token, 1, Integer::sum);
            }
        }

        var result = new TermFrequencyVectorizer();
        result.vocabulary = vocabulary.entrySet().stream()
            .filter(entry -> entry.getValue() > minFreq)
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(maxSize).map(Map.Entry::getKey).collect(Collectors.toList());

        return result;
    }
}
