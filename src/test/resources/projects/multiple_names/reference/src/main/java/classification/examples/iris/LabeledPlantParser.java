package classification.examples.iris;

import java.util.Arrays;

import classification.data.BinaryLabel;
import classification.data.SupervisedSample;
import classification.io.SampleParser;

/**
 * Kann verwendet werden, um ein beschriftetes {@link IrisPlant}-Objekt aus
 * einer Sequenz von Kelchblatt-Länge und -Breite sowie Kronblatt-Länge und
 * -Breite sowie ihrer Art zu erzeugen.
 * Die ersten vier Parameter sollten sich hierfür als {@code double}-Werte
 * parsen lassen und der fünfte sollte einem der Ergebnisse von
 * {@link PlantClass#toString()} entsprechen.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class LabeledPlantParser
    implements SampleParser<SupervisedSample<IrisPlant, BinaryLabel>> {
    private PlantClass positiveClass;
    private PlantParser parser;

    /**
     * Erzeugt einen {@code LabeledPlantParser}, der die übergebene
     * Schwertlilienart als die positive Klasse interpretiert.
     *
     * @param positiveClass die Art, die als Positivbeispiel gewertet werden
     *                      soll. Jede weitere Art zählt als Negativbeispiel.
     */
    public LabeledPlantParser(PlantClass positiveClass) {
        this.positiveClass = positiveClass;
        parser = new PlantParser();
    }

    @Override
    public SupervisedSample<IrisPlant, BinaryLabel> parse(String[] values) {
        var plant = parser.parse(Arrays.copyOfRange(values, 0, 4));

        var label = values[4].equals(positiveClass.toString())
            ? BinaryLabel.POSITIVE
            : BinaryLabel.NEGATIVE;

        return new SupervisedSample<>(plant, label);
    }
}
