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

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private HibernateTemplate getHibernateTemplate(){
        if(null == hibernateTemplate)
            hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }

    @Override
    public List findByString(String key, int pageNum, int maxNum ) {
        List result = null;
        try{
            // 匹配收件人，寄件人，寄信地址
            Query query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                    .createQuery( "from letter l where l.receiver=:receiver or l.sender=:sender or l.senderaddr=:senderaddr");
            query.setString("sender", key);
            query.setString("receiver", key);
            query.setString("senderaddr", key);
            query.setMaxResults(maxNum);
            query.setFirstResult( ( pageNum - 1 ) * maxNum);
            result = getHibernateTemplate().find(query.toString());
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
                getHibernateTemplate().delete(letter);
                return true;
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
    public List getAll(final int pageNum,final int maxNum) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException {
                Query query = session.createQuery("from letter l order by l.time desc");
                query.setMaxResults(maxNum);
                query.setFirstResult( ( pageNum - 1 ) * maxNum);
                return query.list();
            }
        });
    }
}
