package common;

import java.util.Date;
import java.util.*;

/**
 * Created by ferhat on 2015-11-19.
 */
public class Historique  {
    private static final long serialVersionUID=1L;
    private List<ActionReal> actionsReal;
    private static Historique instance=null;

    public Historique(){
        actionsReal=new ArrayList<ActionReal>();
    }

    public static Historique getIsntance(){
        if(instance == null){
            instance = new Historique();
        }
        return instance;
    }
    public void setAction(ActionReal act){
        act.setStatus(ActionReal.Status.ATTENTE);
        actionsReal.add(act);
    }
    public ActionReal recupAction(Date date){

        for(ActionReal obj :actionsReal) {
            if (obj.getDate().equals(date)) {
                return obj;
            }
        }
        return null;
    }
    public List<ActionReal> getActionsReal(){return actionsReal;}

    public  List<String> toStringList(){
        List<String> strs = new ArrayList<String>();
        for(ActionReal act :actionsReal) {
            strs.add(act.toString());
        }
        return strs;
    }
}
