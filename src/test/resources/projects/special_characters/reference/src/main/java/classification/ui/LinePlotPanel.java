package classification.ui;

import java.util.List;
import java.util.stream.IntStream;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Zeigt einen logarithmisch transformierten Plot einer Liste von
 * {@link Double}-Werten in Abhängigkeit ihrer Position in der Liste an.
 *
 * @author Kim Berninger
 * @version 1.1.0
 */
public class LinePlotPanel extends JPanel {
    private static final long serialVersionUID = -8799249138344019204L;

    private List<? extends Number> values;

    /**
     * Erzeugt ein neues {@code LinePlotPanel} mit der übergebenen Liste an
     * y-Werten.
     * Die Liste dient als Modell dieser Komponente.
     * Sie wird von diesem {@code LinePlotPanel} nicht verändert, allerdings
     * spiegeln sich Veränderungen der Liste beim Aufruf von
     * {@link #repaint()} in der erzeugten Grafik wieder.
     *
     * @param values die y-Werte, die in den Plot eingezeichnet werden sollen
     */
    public LinePlotPanel(List<? extends Number> values) {
        this.values = values;

        setPreferredSize(new Dimension(500, 500));
    }
    
    private void drawYAxisLogLines(Graphics g, int n, double min, double max) {
        var g2d = (Graphics2D) g;
        for (var k = 0; k < n; k++) {
            var lineAscent = 0.0;
            var lineValue = min + (max - min) * (k + lineAscent) / n;
            var lineCoord = (int) (getHeight() *
                Math.log(max / lineValue) /
                Math.log(max / min));

            g2d.setColor(Color.GRAY);
            g2d.drawLine(0, lineCoord, getWidth(), lineCoord);

            var label = String.format("%.3f", lineValue);
            var labelWidth = g2d.getFontMetrics().stringWidth(label);
            var labelDescent = g2d.getFontMetrics().getDescent();
            g2d.setColor(Color.WHITE);
            g2d.drawString(label,
                getWidth() - labelWidth,
                lineCoord - labelDescent);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (values.size() < 2) {
            return;
        }

        var statistics = values.stream()
            .mapToDouble(Number::doubleValue)
            .summaryStatistics();

        var yMin = statistics.getMin();
        var yMax = statistics.getMax();

        var dx = (double) getWidth() / (values.size() - 1);
        var dy = (double) getHeight() / Math.log(yMax / yMin);

        drawYAxisLogLines(g, 5, yMin, yMax);

        var xPoints = IntStream.concat(
            IntStream.of(getWidth(), 0),
            IntStream.range(0, values.size())
                .map(x -> (int) (dx * x)))
            .toArray();

        var yPoints = IntStream.concat(
            IntStream.of(getHeight(), getHeight()),
            values.stream()
                .mapToDouble(Number::doubleValue)
                .mapToInt(y -> (int) (dy * Math.log(yMax / y))))
            .toArray();

        g.setColor(new Color(0.3f, 0.6f, 0.9f, 0.5f));
        g.fillPolygon(xPoints, yPoints, values.size()+2);

        g.setColor(new Color(0.3f, 0.6f, 0.9f));
        for (var i = 2; i < values.size()+1; i++) {
            g.drawLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
        }
    }
}
