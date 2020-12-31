package classification;

import java.util.List;

import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import classification.data.Sample;
import classification.data.SupervisedSample;
import classification.data.BinaryLabel;
import classification.data.Dataset;

import classification.models.BinaryClassifier;

import classification.linalg.Vector;

import classification.ui.LinePlotPanel;
import classification.ui.ClassifierPanel;

/**
 * Wird verwendet, um ein binäres Klassifikationsexperiment auszuführen.
 * Zunächst wird der entsprechende Klassifizierer für eine festgelegte Anzahl an
 * Epochen auf den Trainingsdaten trainiert und schließlich auf den Testdaten
 * evaluiert.
 * Die Resultate werden auf der Kommandozeile angezeigt und zusätzlich in einem
 * neuen Fenster visualisiert.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Subtyp von {@link Sample}, dem die Elemente der Datensätze
 *            entsprechen sollen
 *
 * @see Dataset
 * @see BinaryClassifier
 * @see ClassifierPanel
 * @see LinePlotPanel
 */
public class ClassificationExperiment<S extends Sample<? super S>> {
    private static final int BORDER_SIZE = 20;
    private static final Color BORDER_COLOR = new Color(50, 50, 50);

    private String name;

    private BinaryClassifier<S> classifier;

    private Dataset<SupervisedSample<S, BinaryLabel>> trainingSet;
    private Dataset<SupervisedSample<S, BinaryLabel>> evaluationSet;

    private List<Double> losses;

    private int epochs;

    /**
     * Erzeugt ein neues {@code ClassificationExperiment} mit den entsprechenden
     * Parametern.
     * Der Evaluierungsschritt wird beim Ausführen dieses Experiments
     * übersprungen.
     *
     * @param name          der Name des Experiments, der auf der Kommandozeile
     *                      ausgegeben und im Resultatefenster als Titel
     *                      angezeigt werden soll
     * @param classifier    der Klassifizierer, der für das Experiment trainiert
     *                      und evaluiert werden soll
     * @param trainingSet   der Datensatz, auf dem der Klassifizierer trainiert
     *                      werden soll
     * @param epochs        die Anzahl der Iterationen, für die der
     *                      Klassifizierer trainiert werden soll
     */
    public ClassificationExperiment(
        String name,
        BinaryClassifier<S> classifier,
        Dataset<SupervisedSample<S, BinaryLabel>> trainingSet,
        int epochs
    ) {
        this(name, classifier, trainingSet, null, epochs);
    }

    /**
     * Erzeugt ein neues {@code ClassificationExperiment} mit den entsprechenden
     * Parametern.
     *
     * @param name          der Name des Experiments, der auf der Kommandozeile
     *                      ausgegeben und im Resultatefenster als Titel
     *                      angezeigt werden soll
     * @param classifier    der Klassifizierer, der für das Experiment trainiert
     *                      und evaluiert werden soll
     * @param trainingSet   der Datensatz, auf dem der Klassifizierer trainiert
     *                      werden soll
     * @param evaluationSet der Datensatz, auf dem der Klassifizierer evaluiert
     *                      werden soll
     * @param epochs        die Anzahl der Iterationen, für die der
     *                      Klassifizierer trainiert werden soll
     */
    public ClassificationExperiment(
        String name, BinaryClassifier<S> classifier,
        Dataset<SupervisedSample<S, BinaryLabel>> trainingSet,
        Dataset<SupervisedSample<S, BinaryLabel>> evaluationSet,
        int epochs
    ) {
        this.name = name;
        this.classifier = classifier;
        this.trainingSet = trainingSet;
        this.evaluationSet = evaluationSet;
        this.epochs = epochs;
    }

    /**
     * Führt dieses Experiment aus und zeigt die Ergebnisse an.
     */
    public void run() {
        if (classifier == null || trainingSet == null) {
            System.err.format(
                "Experiment \"%s\" konnte nicht durchgeführt werden.%n", name);
            return;
        }

        System.out.println("-".repeat(80));
        System.out.format("Beginnt mit Experiment \"%s\" ...%n", name);

        losses = classifier.fit(trainingSet, epochs);

        System.out.println("Training abgeschlossen.");

        System.out.format("Genauigkeit auf den Trainingsdaten:    %.2f %%.%n",
            100 * classifier.evaluate(trainingSet));

        if (evaluationSet != null) {
            System.out.format(
                "Genauigkeit auf den Evaluierungsdaten: %.2f %%.%n",
                100 * classifier.evaluate(evaluationSet));
        }

        System.out.println("-".repeat(80));

        SwingUtilities.invokeLater(this::showResults);
    }
    
    private static JPanel bordered(JPanel content) {
        var panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createMatteBorder(
            BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_COLOR));
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private void showResults() {
        var frame = new JFrame(name);

        var contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.LINE_AXIS));

        try {
            var svmClass = Class.forName("classification.models.SVM");

            if (classifier.getClass().equals(svmClass)) {
                var svm = svmClass.cast(classifier);

                var getWeights = svmClass.getMethod("getWeights");

                var weights = (Vector) getWeights.invoke(svm);

                if (weights.getDimension() == 2) {
                    var panel = new ClassifierPanel<S>(classifier);

                    panel.addSamples(trainingSet);

                    if (evaluationSet != null) {
                        panel.addSamples(evaluationSet);
                    }

                    contentPane.add(bordered(panel));
                }
            }
        } catch (ReflectiveOperationException e) {
            System.err.println("Unbekannte Unterart von BinaryClassifier.");
        }

        if (losses != null) {
            var linePanel = new LinePlotPanel(losses);
            contentPane.add(bordered(linePanel));
        }

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
