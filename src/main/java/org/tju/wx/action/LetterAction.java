package org.tju.wx.action;

import com.opensymphony.xwork2.ActionSupport;
import org.tju.wx.pojo.Letter;

/**
 * Created by Administrator on 2014/7/21.
 */
public class LetterAction extends ActionSupport{
    private Letter letter;
    public String add(){
        
        return SUCCESS;
    }

    public void setLetter(Letter letter){this.letter = letter;}

    public Letter getLetter() { return letter; }
}
