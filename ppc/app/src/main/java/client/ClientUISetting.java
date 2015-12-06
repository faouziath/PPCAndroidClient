package client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fy.ppc.StartedActivity;
import com.example.fy.ppc.R;

import java.util.List;

import common.Match;
import common.MessageArg;
import common.ObjectArg;
import common.Parie;
import common.Protocol;
import common.TCPMessage;
import common.UDPPacket;

/**
 * Created by fy on 2015-10-18.
 */
public class ClientUISetting {

    public ClientUISetting(){

    }

    public static void setSpinnerStart(ClientActivity activity) {
        //activity.sendReceive(Client.PROTOCOL.UDP, new UDPPacket(Protocol.RequestCode.LISTE_MATCH, new ObjectArg()));
    }

    public static void setSpinnerEnd(ClientActivity activity,List list, int i){
        Spinner spinner = (Spinner)activity.findViewById(i);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line,list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
    }

//    public static void initView_parie(StartedActivity activity)
//    {
//        Button send_parie = (Button)activity.findViewById(R.id.send_parie);
//        send_parie.setOnClickListener(activity);
//        Button back = (Button)activity.findViewById(R.id.back);
//        back.setOnClickListener(activity);
//        Spinner spinner = (Spinner)activity.findViewById(R.id.spinner_parie);
//        spinner.setOnItemSelectedListener(activity);
//    }
//    public static void sendParie(ClientActivity act,String name){
//        EditText pointage = (EditText) act.findViewById(R.id.pointage);
//        String pointageVal = pointage.getText().toString();
//        EditText montant = (EditText) act.findViewById(R.id.montant);
//        int montantVal = Integer.parseInt(montant.getText().toString());
//        Parie parie = new Parie();
//        MessageArg msg = new MessageArg();
//        parie.montant =montantVal;
//        String[] parts = ((String)pointageVal).split("-");
//        int i =  Integer.parseInt(parts[0]);
//        parie.pointage.pointA = i;
//        parie.pointage.pointB = Integer.parseInt(parts[1]);
//        msg.setParie(parie);
//        msg.setMatchName(name);
//        TCPMessage parieMsg = new TCPMessage(Protocol.RequestCode.PUT_BET, msg);
//        act.sendReceive(Client.PROTOCOL.TCP, parieMsg);
//    }
//
//    public static void initView(StartedActivity activity)
//    {
//        Button update = (Button)activity.findViewById(R.id.update);
//        update.setOnClickListener(activity);
//        Button parie = (Button)activity.findViewById(R.id.parie);
//        parie.setOnClickListener(activity);
//        Spinner spinner = (Spinner)activity.findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener(activity);
//    }
//    public static  void getMatchA(ClientActivity act, String name){
//        ObjectArg o = new ObjectArg();
//        String[] parts = ((String)name).split("Vs");
//        o.getMatch().equipeA = parts[0].trim();
//        o.getMatch().equipeB = parts[1].trim();
//        act.sendReceive(Client.PROTOCOL.UDP, new UDPPacket(Protocol.RequestCode.MATCH, o));
//    }
//    public static void getMatchPeriodiqe(ClientActivity act, String name,String match_selected){
//        if(match_selected != null){
//            ObjectArg o = new ObjectArg();
//            String[] parts = ((String)name).split("Vs");
//            o.getMatch().equipeA = parts[0].trim();
//            o.getMatch().equipeB = parts[1].trim();
//            act.periodicSendReceive(Client.PROTOCOL.UDP, new UDPPacket(Protocol.RequestCode.MATCH, o), Protocol.DELAI_MATCH_UPDATE_CLIENT);
//
//        }
//    }
//    public static  void setMatch(ClientActivity act,Match m){
//        TextView v = (TextView)act.findViewById(R.id.pointage);
//        v.setText(m.getPointage().pointA + " - " + m.getPointage().pointB);
//        TextView chrono = (TextView)act.findViewById(R.id.chrono);
//        chrono.setText(formatMatchTime(m.chrono));
//        EditText butsA = (EditText)act.findViewById(R.id.butsA);
//        String bA = m.getButsA().toString();
//        butsA.setText(m.equipeA+":  "+formatList(bA));
//        EditText butsB = (EditText)act.findViewById(R.id.butsB);
//        String bB = m.getButsB().toString();
//        butsB.setText(m.equipeB+":  "+formatList(bB));
//        EditText penA = (EditText)act.findViewById(R.id.penA);
//        String pA = m.getPenaltiesA().toString();
//        penA.setText(m.equipeA+":  "+formatList(pA));
//        EditText penB = (EditText)act.findViewById(R.id.penB);
//        String pB = m.getPenaltiesB().toString();
//        penB.setText(m.equipeB+":  "+formatList(pB));
//
//    }
//
//    public static String formatList(String s){
//        s = s.replaceAll("\\{\\}", "");
//        s = s.replaceAll("="," (");
//        s = s.replaceAll(",", "), ");
//        s = s.replaceAll("\\{", "");
//        s = s.replaceAll("\\}", ")");
//
//        return s;
//    }
//
//    public static  void notifyId(Context context, Parie_activity act,int id){
//        CharSequence text = "Your comfirmation number is!"+ id;
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
//        Intent intent = new Intent(act, Parie_activity.class);
//        act.startActivity(intent);
//    }
//    public static  void setConfirm( Parie_activity act,int id){
//        CharSequence text = "Your comfirmation number is!"+ id;
//        TextView con = (TextView)act.findViewById(R.id.confirm);
//        con.setText(text);
//    }
//    public static String formatMatchTime(int seconds) {
//        String period;
//
//        if (seconds < 1200) {
//            period = "1st";
//        }
//        else if (seconds < 2400) {
//            period = "2nd";
//        }
//        else if (seconds < 3600) {
//            period = "3rd";
//        }
//        else {
//            return "match terminated";
//        }
//
//        int secondsLeft = 1200 - (seconds % 1200);
//
//        int mins = (secondsLeft / 60);
//        int secs = (secondsLeft % 60);
//
//        String mm;
//        if (mins < 10) {
//            mm = "0" + mins;
//        }
//        else {
//            mm = Integer.toString(mins);
//        }
//
//        String ss;
//        if (secs < 10) {
//            ss = secs + "0";
//        }
//        else {
//            ss = Integer.toString(secs);
//        }
//
//        return period + " period - " + mm + ":" + ss;
//    }
}
