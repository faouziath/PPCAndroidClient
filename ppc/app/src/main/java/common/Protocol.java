package common;

import java.net.*;

/**
 * Created by fy on 2015-10-16.
 */
public class Protocol {
    public static final int packetSize = 5000;
    public static final String SERVER_ADDRESS = "10.0.2.2";
    public static final int SERVER_PORT = 1111;
    public enum RequestCode {
        LISTE_MATCH, MATCH, PARIE,NULL_RESPONSE,PUT_BET, TAKE_BET
    }
    public  static final int DELAI_MATCH_UPDATE_CLIENT = 120000;
}
