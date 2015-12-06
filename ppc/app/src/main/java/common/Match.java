package common;

import java.io.Serializable;
import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;  // utility class

/**
 * Created by fy on 2015-10-15.
 */
public class Match implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final String[] members = new String[]{"chrono","equipeA","equipeB","pointage","butsA","butsB","penaltiesA","penaltiesB"};

    public String equipeA;
    public String equipeB;
    public volatile int chrono;
    public Pointage pointage;
    public Map<String,Integer> butsA;
    public Map<String,Integer> butsB;
    public Map<String,Integer> penaltiesA;
    public Map<String,Integer> penaltiesB;
    public List<String> joueursA ;
    public List<String> joueursB ;

    public void setPointage(Pointage pointage) {
        this.pointage = pointage;
    }

    public void setButsA(Map<String, Integer> butsA) {
        this.butsA = butsA;
    }

    public void setButsB(Map<String, Integer> butsB) {
        this.butsB = butsB;
    }

    public void setPenaltiesB(Map penaltiesB) {
        this.penaltiesB = penaltiesB;
    }

    public void setPenaltiesA(Map penaltiesA) {
        this.penaltiesA = penaltiesA;
    }

    public void setJoueursA(List<String> joueursA) {
        this.joueursA = joueursA;
    }

    public void setJoueursB(List<String> joueursB) {
        this.joueursB = joueursB;
    }

    public Map<String, Integer> getButsA() {
        return butsA;
    }

    public Pointage getPointage() {
        return pointage;
    }

    public Map<String, Integer> getButsB() {
        return butsB;
    }

    public Map getPenaltiesA() {
        return penaltiesA;
    }

    public Map getPenaltiesB() {
        return penaltiesB;
    }

    public List<String> getJoueursA() {
        return joueursA;
    }

    public List<String> getJoueursB() {
        return joueursB;
    }


    public Match()
    {
        butsA = new HashMap<String,Integer>();
        butsB = new HashMap<String,Integer>();
        penaltiesA = new HashMap<String,Integer>();
        penaltiesB = new HashMap<String,Integer>();
        //penaltiesB = Collections.synchronizedMap(new HashMap<String,Integer>());
        pointage = new Pointage(0,0);
        equipeA = null;
        equipeB = null;
        chrono = 0;

        joueursA = new ArrayList<String>(6);
        joueursB = new ArrayList<String>(6);

        joueursA.add("Dennis D. Reeves");
        joueursA.add("Leroy K. Nguyen");
        joueursA.add("Herschel N. Percy");
        joueursA.add("Michael R. Wallace");
        joueursA.add("Jonathan B. Lyons");
        joueursA.add("Kelly R. Wile");

        joueursB.add("David B. Cole");
        joueursB.add("Leroy J. Bryan");
        joueursB.add("Thomas C. Johnson");
        joueursB.add("Joseph A. Martinez");
        joueursB.add("David J. Kim");
        joueursB.add("Murray E. Hahne");
    }

    public synchronized void  updateMatch()
    {
        Random randomGenerator = new Random();
        Integer equipeToUpdate = randomGenerator.nextInt(2);
        Integer dataToUpdate = randomGenerator.nextInt(2);
        Integer valueToAddForUpdate = randomGenerator.nextInt(2);

        if (dataToUpdate ==0)
        {
            if(equipeToUpdate ==0){

                if(valueToAddForUpdate==1)
                {
                    pointage.pointA = pointage.pointA + valueToAddForUpdate.intValue();
                    Integer joueurToUpdate = randomGenerator.nextInt(6);
                    Integer i =0;
                    String s = joueursA.get(joueurToUpdate);
                    if(butsA.containsKey(s)){
                        i = butsA.get(s);
                        butsA.remove(s);
                    }
                    int temp = i.intValue() + valueToAddForUpdate.intValue();
                    butsA.put(s, (Integer) temp);
                    String ss = new String("aaa");
                }
            }
            else
            {

                if(valueToAddForUpdate==1)
                {
                    pointage.pointB = pointage.pointB + valueToAddForUpdate.intValue();
                    Integer joueurToUpdate = randomGenerator.nextInt(6);
                    Integer i =0;
                    String s = joueursB.get(joueurToUpdate);
                    if(butsB.containsKey(s)){
                        i = butsB.get(s);
                        butsB.remove(s);
                    }
                    int temp = i.intValue() + valueToAddForUpdate.intValue();
                    butsB.put(s, (Integer) temp);
                }
            }
        }
        else if(dataToUpdate ==1 )
        {
            if(equipeToUpdate ==0){

                if(valueToAddForUpdate==1)
                {
                    Integer joueurToUpdate = randomGenerator.nextInt(6);
                    Integer i =0;
                    String s = joueursA.get(joueurToUpdate);
                    if(penaltiesA.containsKey(s)){
                        i = penaltiesA.get(s);
                        penaltiesA.remove(s);
                    }
                    int temp = i.intValue() + valueToAddForUpdate.intValue();
                    penaltiesA.put(s, (Integer) temp);
                }
            }
            else
            {
                if(valueToAddForUpdate==1)
                {
                    Integer joueurToUpdate = randomGenerator.nextInt(6);
                    Integer i =0;
                    String s = joueursB.get(joueurToUpdate);
                    if(penaltiesB.containsKey(s)){
                        i = penaltiesB.get(s);
                        penaltiesB.remove(s);
                    }
                    int temp = i.intValue() + valueToAddForUpdate.intValue();
                    penaltiesB.put(s, (Integer) temp);
                }
            }
        }
    }

    public  void updateMatchChrono() throws InterruptedException {
        chrono = chrono + 30;
        if (chrono ==3600)
        {

        }
    }

}
