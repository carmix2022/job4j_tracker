package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            session.save(item);
            currentTransaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            item.setId(id);
            session.update(item);
            currentTransaction.commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            currentTransaction.commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return false;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        List<Item> result = session.createQuery("FROM Item", Item.class).list();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        List<Item> result = session.createQuery("FROM Item WHERE name = :fName", Item.class)
                .setParameter("fName", key)
                .list();
        session.close();
        return result;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        Item result = session.get(Item.class, id);
        session.close();
        return result;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
