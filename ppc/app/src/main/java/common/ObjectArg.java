package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fy on 2015-10-15.
 */
public class ObjectArg implements Serializable{
    private static final long serialVersionUID = 1L;
    private Match match;
    private List listeMatch;

    public List getListeMatch() {
        return listeMatch;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setListeMatch(List listeMatch) {
        this.listeMatch = listeMatch;
    }

    public Match getMatch() {
        return match;

    }

    public ObjectArg(Match p_match, List p_listeMatch)
    {
        match = p_match;
        listeMatch = p_listeMatch;


    }
    public ObjectArg()
    {
        match = new Match();
        listeMatch = new ArrayList<String>(10);

    }
}
