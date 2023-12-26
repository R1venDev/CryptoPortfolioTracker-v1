package com.example.cryptoapp.repositories;


import com.example.cryptoapp.models.BaseModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class GenericRepository<T extends BaseModel> implements IRepository<T> {
    private SessionFactory sessionFactory;
    private final String query;
    private final Class<T> classType;

    public GenericRepository(Class<T> type, String query, SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.classType = type;
        this.query = query;
    }

    @Override
    public void save(T entity) {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
        catch(Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex.getClass().getName() + "occured: \n" + ex.getMessage());
        }
    }

    @Override
    public T findById(Long id) {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(this.classType, id);
            transaction.commit();
            return entity;
        }
        catch(Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex.getClass().getName() + "occured: \n" + ex.getMessage());
            return null;
        }
        finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    @Override
    public List<T> findAll() {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<T> entities = session.createQuery(this.query, this.classType).list();
            transaction.commit();
            return entities;
        }
        catch(Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex.getClass().getName() + "occured: \n" + ex.getMessage());
            return new ArrayList<T>();
        }
        finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    @Override
    public void update(T entity) throws NullPointerException {
        if(entity == null) {
            throw new NullPointerException(this.classType.getName() + " to update cannot be null.");
        }

        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
        catch(Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex.getClass().getName() + "occured: \n" + ex.getMessage());
        }
        finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    @Override
    public void delete(Long id) throws NullPointerException {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(this.classType, id);
            if(entity != null) {
                session.remove(entity);
                transaction.commit();
            }
            else {
                transaction.rollback();
                throw new NullPointerException(this.classType.getName() + "to delete wasn't found.");
            }
        }
        catch(Exception ex) {
            if(transaction != null) {
                transaction.rollback();
            }
            System.out.println(ex.getClass().getName() + "occured: \n" + ex.getMessage());
        }
        finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
}