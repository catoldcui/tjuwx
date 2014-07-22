package org.tju.wx.pojo;

/**
 * Created by Administrator on 2014/7/21.
 */
public class Letter {
    /**
     * 在团委
     */
    public static int IN = 1;

    /**
     * 已被取走
     */
    public static int SENDED = 2;
    private int lid;
    private String receiver;
    private String sender;
    private String senderaddr;

    /**
     * 寄送时间，如果没有的话，存入添加数据时间
     */
    private String time;


    /**
     *1：信件在团委 2：信件已取
     */
    private int status;

    /**
     * 显示的页码数
     */

    public String getSenderaddr() {
        return senderaddr;
    }

    public void setSenderaddr(String senderaddr) {
        this.senderaddr = senderaddr;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLid(){ return lid; }

    public void setLid(int lid){ this.lid = lid; }

    public String getReceiver(){ return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public void setSender(String sender){ this.sender = sender; }

    public String getSender(){ return sender; }

    public void setDate(String time){ this.time = time; }

    public String getDate(){ return time; }

    public int getStatus(){ return status; }

    public void setStatus(int status) { this.status = status; }
}
