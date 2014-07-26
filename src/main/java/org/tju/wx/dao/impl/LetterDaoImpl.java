package org.tju.wx.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.tju.wx.dao.LetterDao;
import org.tju.wx.pojo.Letter;

import java.util.List;

/**
 * Created by Administrator on 2014/7/21.
 */
public class LetterDaoImpl implements LetterDao{
    SessionFactory sessionFactory;
    HibernateTemplate hibernateTemplate;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private HibernateTemplate getHibernateTemplate(){
        if(null == hibernateTemplate)
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }

    @Override
    public List findByString(final String key, final int pageNum, final int maxNum ) {
        List result = null;
        try{
            // 匹配收件人，寄件人，寄信地址
//            String[] params = {key, key, key};
//            getHibernateTemplate().find("from Letter l where l.receiver like ':receiver' or l.sender like ':sender' or l.senderaddr like ':senderaddr'", params);
//            Query query = sessionFactory.getCurrentSession()
//                    .createQuery( "from Letter l where l.receiver like ':receiver' or l.sender like ':sender' or l.senderaddr like ':senderaddr'");
//            query.setString("sender", key);
//            query.setString("receiver", key);
//            query.setString("senderaddr", key);
//            query.setMaxResults(maxNum);
//            query.setFirstResult( ( pageNum - 1 ) * maxNum);
//            result = getHibernateTemplate().find(query.toString());

            result = getHibernateTemplate().executeFind(new HibernateCallback() {
                public Object doInHibernate(Session session)
                        throws HibernateException {
                    String keyTemp = "'%" + key + "%'";
//                    Query query = session.createQuery("from Letter l where l.receiver like :receiver or l.sender like :sender or l.senderaddr like :senderaddr");
//                    query.setString("sender", keyTemp);
//                    query.setString("receiver", keyTemp);
//                    query.setString("senderaddr", keyTemp);
//                    Query query = session.createQuery("from Letter l where l.receiver like ? or l.sender like ? or l.senderaddr like ? order by l.status");
//                    query.setString(0, keyTemp);
//                    query.setString(1, keyTemp);
//                    query.setString(2, keyTemp);

                    Query query = session.createQuery("from Letter l where l.receiver like "+ keyTemp + " or l.sender like "+ keyTemp + " or l.senderaddr like "+ keyTemp + " order by l.status, l.time desc");
                    if(maxNum != Integer.MAX_VALUE){
                        query.setFirstResult( ( pageNum - 1 ) * maxNum);
                        query.setMaxResults(maxNum);
                    }

                    System.out.println("query:" + query.getQueryString() + query.toString());
                    return query.list();
                }
            });
        } catch (HibernateException he){
            he.printStackTrace();
        } finally {
            return result;
        }
    }

    @Override
    public boolean add(Letter letter) {
        if(letter != null) {
            try {
                getHibernateTemplate().save(letter);
                return true;
            } catch (HibernateException he) {
                he.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Letter letter) {
        if(letter != null){
            try{
                if(findById(letter.getLid()) != null){
                    getHibernateTemplate().delete(letter);
                    return true;
                } else {
                    return false;
                }

            } catch (HibernateException he){
                he.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean update(Letter letter) {
        if(letter != null) {
            try {
                getHibernateTemplate().update(letter);
                return true;
            } catch (HibernateException he) {
                he.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public Letter findById(int lid) {
        List result = getHibernateTemplate().find("from Letter l where l.lid=?", lid);
        if(result.isEmpty()){
            return null;
        } else {
            return (Letter)result.get(0);
        }
    }

    @Override
    public List getAll(final int pageNum,final int maxNum) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery("from Letter l order by l.status, l.time desc");
                if(maxNum != Integer.MAX_VALUE){
                    query.setFirstResult( ( pageNum - 1 ) * maxNum);
                    query.setMaxResults(maxNum);
                }
                return query.list();
            }
        });
    }
}
