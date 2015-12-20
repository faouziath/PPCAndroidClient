package client;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fy.ppc.AddActionreal;
import com.example.fy.ppc.ConnexionActivity;
import com.example.fy.ppc.HistoryActivity;
import com.example.fy.ppc.R;
import com.example.fy.ppc.WelcomeActivity;

import java.util.ArrayList;
import java.util.List;

import common.Couple;
import common.Message;


/**
 * Created by fy on 2015-10-18.
 */
public class ClientUISetting {
    public enum ActivityType { CONNEXION, WELCOME, CREATE_ACTION ,ADD_COUPLE, ACTION_PROCESS }
    public ClientUISetting(){

    }

    public static void setSpinnerStart(ClientActivity act) {
        Message msgSn = new Message();
        msgSn.setSubject(Message.Subject.ACTIONS);
        act.sendReceive(msgSn);
    }

    public static void setSpinnerEnd(ClientActivity activity,List list, int i){
        Spinner spinner = (Spinner)activity.findViewById(i);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line,list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();
    }
    public static void setUserInfo(ConnexionActivity act, String userName, Couple couple){
        if(couple == null){
            act.currentUserId = null;
            TextView test = (TextView)act.findViewById(R.id.lblLoginInc);
            test.setText("Incorrect, reessayez SVP");
        }
        else{
            act.currentCouple = couple;
            Intent intent = new Intent(act, WelcomeActivity.class);
            intent.putExtra("currentCouple", couple);
            intent.putExtra("currentUserId", userName);
            act.startActivity(intent);
        }

    }

    public  static  void setWelcomInfo(WelcomeActivity act){

        Couple couple = (Couple) act.getIntent().getSerializableExtra("currentCouple");
        String userName = (String) act.getIntent().getSerializableExtra("currentUserId");
        act.currentCouple = couple;
        act.currentUserId = userName;
        TextView user1 = (TextView)act.findViewById(R.id.lblpatner1Wel);
        user1.setText(couple.getPartener1().getNom());
        TextView user2 = (TextView)act.findViewById(R.id.lblpatner2Wel);
        user2.setText(couple.getPartener2().getNom());
        TextView point1 = (TextView)act.findViewById(R.id.PointMonsieurId);
        point1.setText(Integer.toString(couple.GetPCpartener1()));
        TextView point2 = (TextView)act.findViewById(R.id.PointMadameId);
        point2.setText(Integer.toString(couple.GetPCpartener2()));
    }

    public static void connectUser(ConnexionActivity act){
        TextView test = (TextView)act.findViewById(R.id.lblLoginInc);
        test.setText("");
        EditText id = (EditText) act.findViewById(R.id.userId);
        String idVal = id.getText().toString();
        act.currentUserId = idVal;
        EditText pass = (EditText) act.findViewById(R.id.txtPassword);
        String passVal = pass.getText().toString();
        Message msgSn = new Message();
        ArrayList<String> list = new ArrayList<String>(2);
        list.add(idVal);
        list.add(passVal);
        msgSn.setSubject(Message.Subject.CONNECT);
        msgSn.setBody(list);
        act.sendReceive(msgSn);
    }

    public static void initComponent(ClientActivity act, ActivityType type)
    {
        switch (type){
        case CONNEXION :
            Button connect = (Button)act.findViewById(R.id.ConnectButtonId);
            connect.setOnClickListener((ConnexionActivity)act);
            break;
        case CREATE_ACTION:

            Couple couple = (Couple) act.getIntent().getSerializableExtra("currentCouple");
            String userName = (String) act.getIntent().getSerializableExtra("currentUserId");
            ((AddActionreal)act).currentCouple = couple;
            ((AddActionreal)act).currentUserId = userName;
            Button send = (Button)act.findViewById(R.id.SendButtonAR);
            send.setOnClickListener((AddActionreal)act);
            Spinner spinner = (Spinner)act.findViewById(R.id.SpinnerAcAR);
            spinner.setOnItemSelectedListener((AddActionreal)act);
            break;
        case WELCOME:
            Button ppc = (Button)act.findViewById(R.id.ActionButton);
            ppc.setOnClickListener((WelcomeActivity)act);
            Button lri = (Button)act.findViewById(R.id.HistoryButton);
            lri.setOnClickListener((WelcomeActivity)act);
            Button web = (Button)act.findViewById(R.id.TOUButton);
            web.setOnClickListener((WelcomeActivity)act);
            break;

        }
    }

    public static void sendActionR(AddActionreal act){
        Message msgSn = new Message();
        ArrayList<String> list = new ArrayList<String>(3);
        list.add(act.currentUserId);
        list.add(act.evaluer);
        list.add(act.actionDescToSend);
        msgSn.setSubject(Message.Subject.ADD_AR);
        msgSn.setBody(list);
        act.sendReceive(msgSn);
    }

    public static void setGrid(HistoryActivity act){
        GridView grid = (GridView)act.findViewById(R.id.gridView);
        Couple couple = (Couple) act.getIntent().getSerializableExtra("currentCouple");
        String userName = (String) act.getIntent().getSerializableExtra("currentUserId");
        act.currentCouple = couple;
        act.currentUserId = userName;
        Message msgSn = new Message();
        msgSn.setSubject(Message.Subject.HISTORIQUE);
        ArrayList<String> list = new ArrayList<String>(2);
        list.add(userName);
        list.add(couple.getPatner(userName));
        msgSn.setBody(list);
        act.sendReceive(msgSn);
    }
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
//    public static void initView(AddCoupleActivity activity)
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
