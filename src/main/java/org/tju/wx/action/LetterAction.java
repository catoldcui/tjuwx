package org.tju.wx.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.ApplicationContext;
import org.tju.wx.pojo.Letter;
import org.tju.wx.service.LetterService;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2014/7/21.
 */
public class LetterAction extends ActionSupport implements ModelDriven<Letter>{
    private Letter letter = new Letter();
    private LetterService letterService;

    /**
     * 每页的数量
     */
    private static int ELENUM = 10;
    private int pageNum;

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public LetterService getLetterService() {
        return letterService;
    }

    public void setLetterService(LetterService letterService) {
        this.letterService = letterService;
    }

    /**
     * 添加信件
     * @return
     */
    public String add(){
        // 设置信未取状态
        letter.setStatus(Letter.IN);

//        // 寄信时间未设置时，将时间设置为当前填入时间
//        if(letter.getTime() == null || letter.getTime().isEmpty() ){
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            letter.setTime(df.format(new Date()));
//        }

        if(letterService.add(letter)){
//            ActionContext.getContext().getSession().put("message", "添加成功");
            addActionMessage("添加成功。编号：" + letter.getLid());
            letter = new Letter();
            return SUCCESS;
        } else {
//            addFieldError("addError", "添加信息失败，可能原因：数据库连接失败;填写数据失败");
//            ActionContext.getContext().getSession().put("message", "添加信息失败，可能原因：数据库连接失败;填写数据失败");
            addActionError("添加信息失败，可能原因：数据库连接失败;填写数据失败");
            return INPUT;
        }
    }

    /**
     * 分页获得所有信件
     * @return
     */
    public String getAll(){
        // pageNum小于等于0 恢复第一页
        System.out.println("pageNum:" + pageNum);
        if(pageNum <= 0){
            pageNum = 1;
        }
        int count = getCount();
        int totalPageNum = count / ELENUM + 1;

        //pagenum大于最大页，恢复最后一页
        if(pageNum > totalPageNum){
            pageNum = totalPageNum;
        }
        ActionContext.getContext().getSession().put("pageNum", pageNum);
        ActionContext.getContext().getSession().put("totalpage", totalPageNum);
        ActionContext.getContext().getSession().put("count", count);

        List list = letterService.getAll(pageNum, ELENUM);
        ActionContext.getContext().getSession().put("letterlist", list);
        return SUCCESS;
    }

    /**
     * 获得总共个数
     * @return
     */
    private int getCount(){
        // 设置每页为最大个数,取出总数
        List list = letterService.getAll(1, Integer.MAX_VALUE);
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public void setLetter(Letter letter){ this.letter = letter;}

    public Letter getLetter() { return letter; }

    @Override
    public Letter getModel() {
        return letter;
    }
}
