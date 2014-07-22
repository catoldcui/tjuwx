package org.tju.wx.dao;

import org.tju.wx.pojo.Letter;

import java.util.List;

/**
 * Created by Administrator on 2014/7/21.
 */
public interface LetterDao {
    /**
     * 通过关键字查询信件信息，匹配寄信人、收信人、寄信地址
     * @param key 关键字
     * @param pageNum 页数
     * @param maxNum 每页数量
     * @return
     */
    public List findByString(String key, int pageNum, int maxNum );
    boolean add(Letter letter);
    boolean delete(Letter letter);
    boolean update(Letter letter);

    /**
     * 通过lid查询
     * @param lid
     * @return
     */
    Letter findById(int lid);

    /**
     * 获得全部新建信息，时间降序排序，分页返回
     * @param pageNum 页数
     * @param maxNum 每页数量
     * @return
     */
    List getAll(int pageNum, int maxNum);
}
