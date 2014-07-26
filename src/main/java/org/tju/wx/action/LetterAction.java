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
import java.util.Objects;

/**
 * Created by Administrator on 2014/7/21.
 */
public class LetterAction extends ActionSupport implements ModelDriven<Letter>{
    private Letter letter = new Letter();
    private LetterService letterService;

    /**
     * 每页的数量
     */
    public static int ELENUM = 5;
    private String pageNum;
    /**
     * 上一个链接
     */
    private String preAction;

    /**
     * 查询的关键字，1-10个字
     */
    private String key;

    public String getPreAction() {
        return preAction;
    }

    public void setPreAction(String preAction) {
        this.preAction = preAction;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageNum() {
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
        //System.out.println("pageNum:" + pageNum);
        int pageN = 0;
        try{
            pageN = Integer.parseInt(pageNum);
        } catch (Exception e){
            return ERROR;
        }

        if(pageN <= 0){
            pageN = 1;
        }
        int count = getCount();
        int totalPageNum = (count - 1) / ELENUM + 1;

        //pagenum大于最大页，恢复最后一页
        if(pageN > totalPageNum){
            pageN = totalPageNum;
        }
        ActionContext.getContext().getSession().put("pageNum", pageN);
        ActionContext.getContext().getSession().put("totalpage", totalPageNum);
        ActionContext.getContext().getSession().put("count", count);

        List list = letterService.getAll(pageN, ELENUM);
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

    /**
     * 删除信
     * @return
     */
    public String delete(){
        System.out.println("delete lid:" + letter.getLid());

        if(letter.getLid() == 0){
            addActionError("删除失败");
        } else if(!letterService.delete(letter)){
            addActionError("删除失败");
        } else {
            addActionMessage("删除成功");
        }

        // 记录返回页
        String resultPage = "";
        if(preAction != null){

            // 获得pageNum
            if(ActionContext.getContext().getSession().get("pageNum") == null){
                pageNum = "1";
            } else {
                pageNum = ActionContext.getContext().getSession().get("pageNum").toString();
            }
            // 判断前一个动作
            if(preAction.equals("add")){
                resultPage = "success_from_add";
                getAll(); // 重新更新
            } else if(preAction.equals("search")){
                resultPage = "success_from_search";
                search();// 重新更新
            } else {
                resultPage = ERROR;
            }
        } else {
            resultPage = ERROR;
        }
        return resultPage;
    }

    /**
     * 取走信
     * @return
     */
    public String send(){
        System.out.println("sender lid:" + letter.getLid());

        if(letter.getLid() == 0){
            addActionError("更新失败");
        } else if(!letterService.sendLetter(letter)){
            addActionError("更新失败");
        } else {
            addActionMessage("更新成功");
        }

        // 记录返回页
        String resultPage = "";
        if(preAction != null){

            // 获得pageNum
            if(ActionContext.getContext().getSession().get("pageNum") == null){
                pageNum = "1";
            } else {
                pageNum = ActionContext.getContext().getSession().get("pageNum").toString();
            }
            // 判断前一个动作
            if(preAction.equals("add")){
                resultPage = "success_from_add";
                getAll(); // 重新更新
            } else if(preAction.equals("search")){
                resultPage = "success_from_search";
                search();// 重新更新
            } else {
                resultPage = ERROR;
            }
        } else {
            resultPage = ERROR;
        }
        return resultPage;
    }

    /**
     * 取回信
     * @return
     */
    public String back(){
        System.out.println("back lid:" + letter.getLid());

        if(letter.getLid() == 0){
            addActionError("更新失败");
        } else if(!letterService.backLetter(letter)){
            addActionError("更新失败");
        } else {
            if(ActionContext.getContext().getSession().get("pageNum") == null){
                pageNum = "1";
            } else {
                pageNum = ActionContext.getContext().getSession().get("pageNum").toString();
            }
            addActionMessage("更新成功");
        }

        // 记录返回页
        String resultPage = "";
        if(preAction != null){
            // 获得pageNum
            if(ActionContext.getContext().getSession().get("pageNum") == null){
                pageNum = "1";
            } else {
                pageNum = ActionContext.getContext().getSession().get("pageNum").toString();
            }
            // 判断前一个动作
            if(preAction.equals("add")){
                resultPage = "success_from_add";
                getAll(); // 重新更新
            } else if(preAction.equals("search")){
                resultPage = "success_from_search";
                search();// 重新更新
            } else {
                resultPage = ERROR;
            }
        } else {
            resultPage = ERROR;
        }
        return resultPage;
    }

    /**
     * 通过关键字查询信件信息
     * @return
     */
    public String search(){
        System.out.println("key: " + key + "\npageNum: " + pageNum);
        if(key == null){
            return SUCCESS;
        }
        if(key.length() < 1 || key.length() > 20){
            return SUCCESS;
        }

        // pageNum小于等于0 恢复第一页
        //System.out.println("pageNum:" + pageNum);
        int pageN = 0;
        try{
            pageN = Integer.parseInt(pageNum);
        } catch (Exception e){
            return SUCCESS;
        }

        if(pageN <= 0){
            pageN = 1;
        }
        List allResult = letterService.findByString(key, 1, Integer.MAX_VALUE);

        int count = 0;
        if(allResult != null){
            count  = allResult.size();
        } else {
            return SUCCESS;
        }

        int totalPageNum = (count - 1) / ELENUM + 1;

        //pagenum大于最大页，恢复最后一页
        if(pageN > totalPageNum){
            pageN = totalPageNum;
        }
        ActionContext.getContext().getSession().put("searchPageNum", pageN);
        ActionContext.getContext().getSession().put("searchTotalpage", totalPageNum);
        ActionContext.getContext().getSession().put("searchCount", count);
        ActionContext.getContext().getSession().put("key", key);

        List list = letterService.findByString(key, pageN, ELENUM);
        ActionContext.getContext().getSession().put("searchLetterlist", list);

        if(list == null || list.size() == 0){
            addActionMessage("暂时没有关于“"+ key + "”的信件");
        } else {
            addActionMessage("查询成功");
        }
        return SUCCESS;
    }

    public void setLetter(Letter letter){ this.letter = letter; }

    public Letter getLetter() { return letter; }

    @Override
    public Letter getModel() {
        return letter;
    }
}
