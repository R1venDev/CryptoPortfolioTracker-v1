package com.example.cryptoapp.repositories;

import com.example.cryptoapp.models.User;

import org.hibernate.*;

import java.util.List;

public class UserRepository implements IRepository<User> {
        private SessionFactory sessionFactory;

        public UserRepository(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

    @Override
    public void save(User user) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            transaction.commit();

            return user;
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            return users;
        }
    }

    @Override
    public void update(User user) throws NullPointerException {
        try (Session session = sessionFactory.getCurrentSession()) {
            if(user == null) {
                throw new NullPointerException("User to update cannot be null.");
            }

            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) throws NullPointerException {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if(user != null) {
                session.delete(user);
                transaction.commit();
            }
            else {
                transaction.rollback();
                throw new NullPointerException("User to delete wasn't found.");
            }
        }
    }
}


