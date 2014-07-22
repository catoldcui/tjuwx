package org.tju.wx.pojo;

/**
 * Created by Administrator on 2014/7/21.
 */
public class Letter {
    private int lid;
    private String receiver;
    private String sender;
    private String senderAddr;
    private String time;
    /**
     *1：信件在团委 2：信件已取
     */
    private int status;

    public int getLid(){ return lid; }

    public void setLid(int lid){ this.lid = lid; }

    public String getReceiver(){ return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public void setSender(String sender){ this.sender = sender; }

    public String getSender(){ return sender; }

    public void setSenderAddr(String senderAddr){ this.senderAddr = senderAddr; }

    public String getSenderAddr(){ return senderAddr; }

    public void setDate(String time){ this.time = time; }

    public String getDate(){ return time; }

    public int getStatus(){ return status; }

    public void setStatus(int status) { this.status = status; }
}
