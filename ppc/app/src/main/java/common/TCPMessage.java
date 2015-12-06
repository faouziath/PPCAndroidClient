package common;

import common.ObjectArg;

import java.io.Serializable;

/**
 * Created by fy on 2015-09-20.
 */
public class TCPMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public Protocol.RequestCode requestCode ;
    public MessageArg argg;


    public TCPMessage(Protocol.RequestCode p_requestCode, MessageArg p_argg )
    {
        this.requestCode = p_requestCode;
        this.argg = p_argg;
    }
    public TCPMessage()
    {
        this.requestCode = Protocol.RequestCode.NULL_RESPONSE;
        this.argg = new MessageArg();
    }
}
