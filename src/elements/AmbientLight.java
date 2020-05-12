package elements;

import primitives.Color;

/**
 *A department representing ambient lighting
 */
public class AmbientLight {

    private Color _intensity;

    /**
     * AmbientLight constructor
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia , double Ka){
        _intensity = Ia.scale(Ka);
    }

    /**
     * intensity getter
     * @return intensity
     */
    public Color GetIntensity() {
        return _intensity;
    }
}
