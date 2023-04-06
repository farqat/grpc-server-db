package com.example.grpcserverdb.dao;

import com.example.grpcserverdb.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.NoSuchElementException;

public class PersonDao {
    public Person findById(Long id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("person-management-system");
        EntityManager em = emf.createEntityManager();

        Person person = em.find(Person.class, id);

        if (person == null) {
            throw new NoSuchElementException("NO DATA FOUND WITH THE ID " + id);
        }

        return person;
    }

    public List<?> findAll(Integer orgId){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("person-management-system");
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT person from Person  person where person.orgid = ?1").setParameter(1, orgId).getResultList();
    }
}
