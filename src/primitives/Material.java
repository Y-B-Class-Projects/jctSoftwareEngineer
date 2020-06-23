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
        this(kD , kS , nShininess, 0 , 0 );
    }

    /***
     * constructor to initialize all the property
     * @param kD the energy percent of Il that goes to the diffuse component
     * @param kS the energy percent of Il that goes to the specular component
     * @param nShininess light is exponentially reduced.
     * @param kT the factor of the transparency שקיפות
     * @param kR the factor of the reflection השתקפות
     */
    public Material(double kD, double kS,  int nShininess, double kT, double kR) {
        this.kD = kD;
        this.kS = kS;
        this.kT = kT;
        this.kR = kR;
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
