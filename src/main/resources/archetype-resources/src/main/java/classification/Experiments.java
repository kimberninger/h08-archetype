package classification;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.WindowConstants;

import classification.models.BinaryClassifier;

import classification.data.Dataset;
import classification.data.Sample;

import classification.io.CSVDatasetLoader;
import classification.examples.iris.IrisPlant;
import classification.examples.iris.PlantClass;
import classification.examples.iris.LabeledPlantParser;

import classification.examples.twodimensional.Point;
import classification.examples.twodimensional.LabeledPointParser;

import classification.examples.sms.Message;
import classification.examples.sms.TermFrequencyVectorizer;
import classification.examples.sms.LabeledMessageParser;

/**
 * @author Kim Berninger
 * @version 1.1.0
 */
public class Experiments {
    private static Map<String, Supplier<ClassificationExperiment<?>>>
        experiments = new TreeMap<>(Map.of(
            "Iris (Setosa)", () -> irisExample(PlantClass.SETOSA),
            "Iris (Versicolor)", () -> irisExample(PlantClass.VERSICOLOR),
            "Iris (Virginica)", () -> irisExample(PlantClass.VIRGINICA),
            "Linear separierbare Punkte", Experiments::small2DExample,
            "Überlappende Gauß-Cluster", Experiments::big2DExample,
            "Beispiel vom Übungsblatt", Experiments::exerciseSheetExample,
            "SMS Spam", Experiments::smsExample));

    public static void main(String[] args) {
        var frame = new JFrame("Experimente");

        var contentPane = frame.getContentPane();

        var layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        var verticalGroup = layout.createSequentialGroup();
        var horizontalGroup = layout.createParallelGroup(
            GroupLayout.Alignment.LEADING);

        experiments.forEach((key, value) -> {
            var button = new JButton(key);
            button.addActionListener(e -> value.get().run());

            horizontalGroup.addComponent(button,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE);

            verticalGroup.addComponent(button,
                GroupLayout.DEFAULT_SIZE,
                GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE);
        });

        layout.setVerticalGroup(verticalGroup);
        layout.setHorizontalGroup(horizontalGroup);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static ClassificationExperiment<IrisPlant> irisExample(
        PlantClass positiveClass) {
        try {
            var trainingSet = Dataset.load(new CSVDatasetLoader<>(
                new File(IrisPlant.class.getResource("iris_train.csv").toURI()),
                new LabeledPlantParser(positiveClass)));
    
            var evaluationSet = Dataset.load(new CSVDatasetLoader<>(
                new File(IrisPlant.class.getResource("iris_eval.csv").toURI()),
                new LabeledPlantParser(positiveClass)));
    
            BinaryClassifier<IrisPlant> svm = svm(4, 0.01);
    
            return new ClassificationExperiment<>(positiveClass.toString(),
                svm, trainingSet, evaluationSet, 1000);
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private static ClassificationExperiment<Point> small2DExample() {
        try {
            var trainingSet = Dataset.load(new CSVDatasetLoader<>(
                new File(Point.class.getResource("points_small_train.csv")
                    .toURI()),
                new LabeledPointParser()));
    
            BinaryClassifier<Point> svm = svm(2, 0.001);
    
            return new ClassificationExperiment<>("Linear separierbare Punkte",
                svm, trainingSet, 200);
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private static ClassificationExperiment<Point> big2DExample() {
        try {
            var trainingSet = Dataset.load(new CSVDatasetLoader<>(
                new File(Point.class.getResource("points_big_train.csv")
                    .toURI()),
                new LabeledPointParser()));
    
            var evaluationSet = Dataset.load(new CSVDatasetLoader<>(
                new File(Point.class.getResource("points_big_eval.csv")
                    .toURI()),
                new LabeledPointParser()));
    
            BinaryClassifier<Point> svm = svm(2, 0.001);
    
            return new ClassificationExperiment<>("Überlappende Gauß-Cluster",
                svm, trainingSet, evaluationSet, 5000);
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private static ClassificationExperiment<Point> exerciseSheetExample() {
        try {
            var trainingSet = Dataset.load(new CSVDatasetLoader<>(
                new File(Point.class.getResource("example.csv").toURI()),
                new LabeledPointParser()));
    
            BinaryClassifier<Point> svm = svm(2, 0.001);
    
            return new ClassificationExperiment<>("Beispiel vom Übungsblatt",
                svm, trainingSet, 500);
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    private static ClassificationExperiment<Message> smsExample() {
        try {
            var trainingSet = Dataset.load(new CSVDatasetLoader<>(
                new File(Message.class.getResource("sms_train.csv").toURI()),
                "\t", new LabeledMessageParser()));
    
            var evaluationSet = Dataset.load(new CSVDatasetLoader<>(
                new File(Message.class.getResource("sms_eval.csv").toURI()),
                "\t", new LabeledMessageParser()));
    
            System.out.println("Bereitet Vokabular vor ...");
    
            var vocabulary = TermFrequencyVectorizer.fromDataset(
                trainingSet, 250, 4);
    
            System.out.println(
                "Berechnet Feature-Vektoren der Trainingsdaten ...");
    
            for (var message : trainingSet) {
                message.getSample().computeFeatures(vocabulary);
            }
    
            System.out.println("Berechnet Feature-Vektoren der Testdaten ...");
    
            for (var message : evaluationSet) {
                message.getSample().computeFeatures(vocabulary);
            }
    
            BinaryClassifier<Message> svm = svm(vocabulary.size(), 0.01);
    
            return new ClassificationExperiment<>("SMS Spam",
                svm, trainingSet, evaluationSet, 100);
        } catch (URISyntaxException ex) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static <S extends Sample<? super S>> BinaryClassifier<S> svm(
        int dimension, double lambda) {
        try {
            return Class.forName("classification.models.SVM")
                .asSubclass(BinaryClassifier.class)
                .getConstructor(int.class, double.class)
                .newInstance(dimension, lambda);
        } catch (ReflectiveOperationException e) {
            System.err.println("SVM-Objekt konnte nicht erzeugt werden!");
        }
        return null;
    }
}
