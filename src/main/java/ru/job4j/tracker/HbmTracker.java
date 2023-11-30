package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

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
            return item;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean replace(int id, Item item) {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            Query query = session.createQuery(
                            "UPDATE Item SET name = :fName, created = :fCreated WHERE id = :fId")
                    .setParameter("fName", item.getName())
                    .setParameter("fCreated", item.getCreated())
                    .setParameter("fId", id);
            var affectedRows = query.executeUpdate();
            currentTransaction.commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            Query query = session.createQuery(
                            "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id);
            var affectedRows = query.executeUpdate();
            currentTransaction.commit();
            return affectedRows > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            List<Item> result = session.createQuery("FROM Item", Item.class).list();
            currentTransaction.commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public List<Item> findByName(String key) {
    Session session = sf.openSession();
        try (session) {
        Transaction currentTransaction = session.beginTransaction();
        List<Item> result = session.createQuery("FROM Item WHERE name = :fName", Item.class)
                .setParameter("fName", key)
                .list();
        currentTransaction.commit();
        return result;
    } catch (Exception e) {
        session.getTransaction().rollback();
        throw e;
    }
}

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        try (session) {
            Transaction currentTransaction = session.beginTransaction();
            Item result = session.get(Item.class, id);
            currentTransaction.commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
