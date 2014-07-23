package org.tju.wx.service.impl;

import org.tju.wx.dao.LetterDao;
import org.tju.wx.pojo.Letter;
import org.tju.wx.service.LetterService;

import java.util.List;

/**
 * Created by Administrator on 2014/7/22.
 */
public class LetterServiceImpl implements LetterService{
    LetterDao letterDao;

    public LetterDao getLetterDao() {
        return letterDao;
    }

    public void setLetterDao(LetterDao letterDao) {
        this.letterDao = letterDao;
    }

    @Override
    public List findByString(String key, int pageNum, int maxNum) {
        List result = null;
        if(key != null && !key.isEmpty()){
            result = letterDao.findByString(key, pageNum, maxNum);
        }
        return result;
    }

    @Override
    public boolean add(Letter letter) {
        // 收件人不为空,状态位置未设置1,返回false
        if(letter.getReceiver() == null || letter.getReceiver().isEmpty() ||
                letter.getStatus() == 0){
            return  false;
        }
        return letterDao.add(letter);
    }

    @Override
    public boolean delete(Letter letter) {
        // 收件人不为空,状态位置未设置1,时间未设置，返回false
        return letterDao.delete(letter);
    }

    @Override
    public boolean update(Letter letter) {
        if(letterDao.findById(letter.getLid()) == null){
            return false;
        }
        return letterDao.update(letter);
    }

    @Override
    public List getAll(int pageNum, int maxNum) {
        // 参数为正
        if(pageNum > 0 && maxNum > 0){
            return letterDao.getAll(pageNum, maxNum);
        }
        return null;
    }

    @Override
    public boolean sendLetter(Letter letter) {
        letter = letterDao.findById(letter.getLid());
        if(letter == null){
            return false;
        }

        letter.setStatus(Letter.SENDED);
        return letterDao.update(letter);
    }

    @Override
    public boolean backLetter(Letter letter) {
        letter = letterDao.findById(letter.getLid());
        if(letter == null){
            return false;
        }

        letter.setStatus(Letter.IN);
        return letterDao.update(letter);
    }
}
