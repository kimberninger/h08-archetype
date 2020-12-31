package classification.ui;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;
import java.awt.Point;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import classification.models.BinaryClassifier;

import classification.data.Dataset;
import classification.data.Sample;
import classification.data.SupervisedSample;
import classification.data.BinaryLabel;

import classification.linalg.Vector;

/**
 * Zeigt eine Menge von binär beschrifteten zweidimensionalen Datenpunkten in
 * einem kartesischen Koordinatensystem zusammen mit der Entscheidungsgrenze
 * eines binären Klassifizierers an.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Subtyp von {@link Sample}, dem die einzuzeichnenden Punkte
 *            entsprechen sollen
 */
public class ClassifierPanel<S extends Sample<? super S>> extends JPanel {
    private static final long serialVersionUID = -2448404976630693234L;

    private BinaryClassifier<? extends S> classifier;
    private List<Dataset<? extends Sample<? super S>>> datasets;

    private Point focus = null;

    /**
     * Erzeugt ein {@code ClassifierPanel} mit dem Klassifizierer, dessen
     * Entscheidungsgrenze in den Plot eingezeichnet werden soll.
     *
     * @param classifier der zu zeichnende Klassifizierer
     */
    public ClassifierPanel(BinaryClassifier<? extends S> classifier) {
        this.classifier = classifier;
        datasets = new ArrayList<>();

        setPreferredSize(new Dimension(500, 500));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                focus = null;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                focus = e.getPoint();
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                focus = e.getPoint();
                repaint();
            }
        });
    }

    /**
     * Fügt einen Datensatz mit zweidimensionalen Datenpunkten zu diesem
     * {@code ClassifierPanel} hinzu.
     *
     * @param samples der Datensatz mit den Punkten, die in den Plot
     *                eingezeichnet werden sollen
     */
    public void addSamples(Dataset<? extends Sample<? super S>> samples) {
        datasets.add(samples);
    }

    private void scatterSamples(Graphics g,
        Dataset<? extends Sample<? super S>> data, Rectangle2D bounds) {
        var xMin = bounds.getMinX();
        var xMax = bounds.getMaxX();
        var yMin = bounds.getMinY();
        var yMax = bounds.getMaxY();

        for (var sample : data) {
            g.setColor(Color.YELLOW);
            if (sample instanceof SupervisedSample<?, ?>) {
                var supervised = (SupervisedSample<?, ?>) sample;
                var label = supervised.getLabel();
                if (label instanceof BinaryLabel) {
                    switch ((BinaryLabel) label) {
                        case POSITIVE:
                            g.setColor(new Color(237, 111, 107));
                            break;
                        case NEGATIVE:
                            g.setColor(new Color(100, 107, 246));
                            break;
                    }
                }
            }

            var phi = sample.getFeatures();

            var x = phi.dot(new Vector(1, 0));
            var y = phi.dot(new Vector(0, 1));

            var cx = (int) (getWidth() * (x - xMin) / (xMax - xMin));
            var cy = (int) (getHeight() * (1 - (y - yMin) / (yMax - yMin)));

            g.fillOval(cx-5, cy-5, 10, 10);
        }
    }

    private void plotDecisionBoundary(Graphics g, Rectangle2D bounds) {
        try {
            var svmClass = Class.forName("classification.models.SVM");

            var svm = svmClass.cast(classifier);

            var getWeights = svmClass.getMethod("getWeights");
            var getBias = svmClass.getMethod("getBias");

            var weights = (Vector) getWeights.invoke(svm);
            var bias = (double) getBias.invoke(svm);

            plotSeparatingLine(g, bounds, weights, bias);
        } catch (ReflectiveOperationException e) {
            System.err.println("Unbekannte Unterart von BinaryClassifier. "
                + "Entscheidungsgrenze konnte nicht gezeichnet werden.");
        }
    }

    private void plotSeparatingLine(Graphics g, Rectangle2D bounds,
        Vector weights, double bias) {

        var w1 = weights.dot(new Vector(1, 0));
        var w2 = weights.dot(new Vector(0, 1));

        var intersections = new ArrayList<Point2D>();
        if (w1 != 0) {
            intersections.add(
                new Point2D.Double(
                    -bias / w1 - w2 * bounds.getMinY() / w1,
                    bounds.getMinY()));
            intersections.add(
                new Point2D.Double(
                    -bias / w1 - w2 * bounds.getMaxY() / w1,
                    bounds.getMaxY()));
        }
        if (w2 != 0) {
            intersections.add(new Point2D.Double(bounds.getMinX(),
                -bias / w2 - w1 * bounds.getMinX() / w2));
            intersections.add(new Point2D.Double(bounds.getMaxX(),
                -bias / w2 - w1 * bounds.getMaxX() / w2));
        }

        var boxIntersections = intersections.stream()
            .filter(p -> p.getX() >= bounds.getMinX()
                && p.getX() <= bounds.getMaxX()
                && p.getY() >= bounds.getMinY()
                && p.getY() <= bounds.getMaxY())
            .limit(2).toArray(Point2D[]::new);

        if (boxIntersections.length == 2) {
            var x1 = (int) (getWidth()
                * (boxIntersections[0].getX() - bounds.getMinX())
                / bounds.getWidth());
            var y1 = (int) (getHeight()
                * (boxIntersections[0].getY() - bounds.getMinY())
                / bounds.getHeight());
            var x2 = (int) (getWidth()
                * (boxIntersections[1].getX() - bounds.getMinX())
                / bounds.getWidth());
            var y2 = (int) (getHeight()
                * (boxIntersections[1].getY() - bounds.getMinY())
                / bounds.getHeight());

            g.setColor(Color.GREEN);
            g.drawLine(x1, getHeight() - y1, x2, getHeight() - y2);
        }
    }

    private void plotFocus(Graphics g, Rectangle2D bounds) {
        var g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(0.1f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
            1.0f, new float[] { 9f }, 0.0f));

        g2d.drawLine(0, focus.y, getWidth(), focus.y);
        g2d.drawLine(focus.x, 0, focus.x, getHeight());

        var x = bounds.getWidth() / getWidth() * focus.x + bounds.getMinX();
        var y = -bounds.getHeight() / getHeight() * focus.y + bounds.getMaxY();

        g2d.drawString(String.format(new Locale("en_US"), "%.2f, %.2f", x, y),
            focus.x + 10, focus.y - 10);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        var xs = new ArrayList<Double>();
        var ys = new ArrayList<Double>();

        for (var dataset : datasets) {
            for (var sample : dataset) {
                var phi = sample.getFeatures();
                xs.add(phi.dot(new Vector(1, 0)));
                ys.add(phi.dot(new Vector(0, 1)));
            }
        }

        var xMin = xs.stream().min(Double::compare).get();
        var xMax = xs.stream().max(Double::compare).get();
        var yMin = ys.stream().min(Double::compare).get();
        var yMax = ys.stream().max(Double::compare).get();

        var bounds = new Rectangle2D.Double(
            xMin, yMin,
            xMax - xMin, yMax - yMin);

        for (var dataset : datasets) {
            scatterSamples(g, dataset, bounds);
        }

        plotDecisionBoundary(g, bounds);

        if (focus != null) {
            plotFocus(g, bounds);
        }
    }
}
