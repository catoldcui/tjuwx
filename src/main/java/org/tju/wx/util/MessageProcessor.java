package org.tju.wx.util;

import org.tju.wx.msgtype.response.NewsMessage;
import org.tju.wx.msgtype.response.TextMessage;
import org.tju.wx.pojo.*;
import org.tju.wx.service.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Legend on 2014/7/18.
 */
public class MessageProcessor {
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = "";
        try {
            // 默认返回的文本消息内容
            String respContent = "";
            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            //回复图文消息
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                // 消息
                String content = requestMap.get("Content");
                //respContent = "您发送的是文本消息！<a href=\"../register.jsp\">这里</a>\n<a href=\"./register.jsp\">这里</a>\n<a href=\"/register.jsp\">这里</a><a href=\"register.jsp\">这里</a>";

                content = content.trim();
                content = content.replace(" ", "");
                content = content.replace("  ", "");
                content = content.replace("\t", "");
                content = content.replace("\n", "");
                content = content.replace("\r", "");

                System.out.print("Receiver: " + content);
                if(content.indexOf("信") == 0) {
                    if (content.length() < 2) {
                        respContent = "请输入正确格式：信+查询关键字。例如：信 张三";
                    } else {
                        // 获得关键字
                        String key = content.substring(1, content.length());
                        LetterService ls = (LetterService)request.getSession().getAttribute("letterService");
                        if(ls == null){
                            respContent = "服务器正在更新,请稍等...";
                            System.out.println("letterservice is null");
                        } else {

                            List list = ls.findByString(key, 1, Integer.MAX_VALUE);
                            if (list == null || list.size() == 0) {
                                respContent = "没有查到有关“" + key + "”的未取信件信息";
                            } else {
                                for (int i = 0; i < list.size(); i++) {
                                    Letter letter = (Letter) list.get(i);
                                    // 不显示已经被取的
                                    if (letter.getStatus() == Letter.SENDED) {
                                        continue;
                                    }
                                    respContent += "\n -------------------\n收件人：" + letter.getReceiver();
                                    if (!"".equals(letter.getSender())) {
                                        respContent += "\n寄件人：" + letter.getSender();
                                    }
                                    if (!"".equals(letter.getSenderaddr())) {
                                        respContent += "\n寄件地址：" + letter.getSenderaddr();
                                    }
                                    respContent += "\n录入时间：" + letter.getTime().substring(0, 10) + "\n";
                                }

                                if(respContent.length() == 0){
                                    respContent = "没有查到有关“" + key + "”的未取信件信息";
                                } else {
                                    respContent = "查询结果：" + respContent;
                                    respContent += "\n请记录好录入时间，及时到团委查询，祝您万事顺利！";
                                }
                            }
                        }
                    }
                    System.out.print("letter:" + respContent);
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "目前暂不支持图片消息的回复！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "地理消息回复功能将在近期开放！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "目前暂不支持链接消息的回复！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "语音消息回复功能将在近期开放！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "感谢您关注天津大学软件学院公众账号[愉快]，\n"
                            + "您可以通过点击<a href=\"../register.jsp\">这里</a> "
                            + "进行身份绑定以获取更多的功能";
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    String eventKey = requestMap.get("EventKey");
                    if(eventKey.equals("31")){
                        respContent = "快递，快递，你要去哪里啊？对我输入快递名称+单号，如 顺丰快递 966902008817~";
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    }else if (eventKey.equals("13")){
                        respContent = "去哪里自习啊？请输入楼号+自习，如 23楼自习，返回当天所有时段自习室。\n" +
                                "如果想查询隔天自习室，在楼号+自习后加上数字代表星期几，如 23楼自习1，返回23楼星期一的自习室。";
                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.textMessageToXml(textMessage);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
