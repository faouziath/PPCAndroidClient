package common;

import java.io.Serializable;

/**
 * Created by fy on 2015-10-15.
 */
public class Parie  implements Serializable{
    private static final long serialVersionUID = 1L;
    public Pointage pointage;
    public int montant;
    public int id;
    public Parie() {
        pointage = new Pointage();
        montant = -1;
        id = -1;
    }
}
