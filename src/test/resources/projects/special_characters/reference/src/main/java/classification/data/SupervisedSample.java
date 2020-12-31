package classification.data;

import classification.linalg.Vector;

/**
 * Modelliert einen beschrifteten Datenpunkt.
 *
 * @author Kim Berninger
 * @version 1.1.0
 *
 * @param <S> der Typ, dessen Vektorrepräsentation durch {@link #getFeatures}
 *            beschrieben werden soll
 * @param <L> der Typ, dem das Label dieses Datenpunktes entsprechen soll
 */
public final class SupervisedSample<S extends Sample<? super S>, L extends Label>
    implements Sample<S> {
    private S sample;
    private L label;

    /**
     * Erzeugt einen beschrifteten Datenpunkt aus dem übergebenen Datenpunkt und
     * dem zugehörigen Label.
     * 
     * @param sample der zu beschriftende Datenpunkt
     * @param label das Label, mit dem dieser Datenpunkt beschriftet sein soll
     */
    public SupervisedSample(S sample, L label) {
        this.sample = sample;
        this.label = label;
    }

    /**
     * Liefert den rohen (potentiell unbeschrifteten) Datenpunkt.
     *
     * @return der Datenpunkt
     */
    public S getSample() {
        return sample;
    }

    /**
     * Liefert das Label dieses Datenpunktes.
     *
     * @return das Label dieses Datenpunktes
     */
    public L getLabel() {
        return label;
    }

    @Override
    public Vector getFeatures() {
        return sample.getFeatures();
    }
}
