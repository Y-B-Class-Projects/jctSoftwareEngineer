package primitives;

/***
 * class material to represent the material of the object,
 * different objects has different type of material
 */

public class Material {

    private double kD, kS, kT, kR;
    private int nShininess;

/********    constructor   *********/
    /***
     * constructor to initialize the properties
     * @param kD the energy percent of Il that goes to the diffuse component
     * @param kS the energy percent of Il that goes to the specular component
     * @param nShininess light is exponentially reduced.
     */
    public Material(double kD, double kS, int nShininess) {
        this.kD = kD;
        this.kS = kS;
        this.nShininess = nShininess;
    }

    /***
     * constructor to initialize all the property
     * @param kD the energy percent of Il that goes to the diffuse component
     * @param kS the energy percent of Il that goes to the specular component
     * @param nShininess light is exponentially reduced.
     * @param kT the factor of the transparency
     * @param kR the factor of the reflection
     */
    public Material(double kD, double kS, double kT, double kR, int nShininess) {
        this.kD = kD;
        this.kS = kS;
        this.kT = 0;
        this.kR = 0;
        this.nShininess = nShininess;
    }
    /********    getter       **********/
    /***
     * kD getter
     * @return the energy percent of Il that goes to the diffuse component
     */
    public double getkD() {
        return kD;
    }

    /***
     * kS getter
     * @return the energy percent of Il that goes to the specular component
     */
    public double getkS() {
        return kS;
    }

    /***
     *  nShininess getter
     * @return the light that is exponentially reduced
     */
    public int getnShininess() {
        return nShininess;
    }

    /***
     * kT getter
     * @return factor of transparency
     */
    public double getkT() {
        return kT;
    }

    /***
     * kR getter
     * @return factor of reflection
     */
    public double getkR() {
        return kR;
    }
}
