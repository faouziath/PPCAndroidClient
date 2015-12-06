package common;

import common.ObjectArg;

import java.io.Serializable;

/**
 * Created by fy on 2015-09-20.
 */
public class UDPPacket implements Serializable {
    private static final long serialVersionUID = 1L;
    public Protocol.RequestCode requestCode ;
    public ObjectArg argg;


    public UDPPacket(Protocol.RequestCode p_requestCode, ObjectArg p_argg )
    {
        this.requestCode = p_requestCode;
        this.argg = p_argg;
    }
    public UDPPacket()
    {
        this.requestCode = Protocol.RequestCode.NULL_RESPONSE;
        this.argg = new ObjectArg();
    }
}
