package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fy on 2015-10-15.
 */
public class MessageArg implements Serializable{
    private static final long serialVersionUID = 1L;
    private Parie parie ;
    private String matchName;


    public Parie getParie() {
        return parie;
    }

    public void setParie(Parie parie) {
        this.parie = parie;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getMatchName() {
        return matchName;

    }

    public MessageArg()
    {
        parie = null;

        matchName = null;
    }
}
