package classification.models;

import java.util.List;

import classification.data.BinaryLabel;
import classification.data.Dataset;
import classification.data.Sample;
import classification.data.SupervisedSample;

/**
 * Dieses Interface definiert die grundlegenden Operationen eines allgemeinen
 * binären Klassifizierers.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Subtyp von {@link Sample}, dem die Elemente, mit denen dieser
 *            Klassifizierer trainiert werden soll, entsprechen sollen
 */
public interface BinaryClassifier<...> {
    <...> List<Double> fit(Dataset<...> data, int epochs);

    <...> double evaluate(Dataset<...> data);

    <...> List<BinaryLabel> predict(Dataset<...> data);
}
