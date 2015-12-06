package common;

import java.io.Serializable;

/**
 * Created by fy on 2015-10-15.
 */
public class Pointage  implements Serializable{
    private static final long serialVersionUID = 1L;
    public int pointA;
    public int pointB;
    public Pointage(int p_pointA, int p_pointB) {
        pointA = p_pointA;
        pointB = p_pointB;
    }
    public Pointage() {
        pointA = 0;
        pointB = 0;
    }
}
