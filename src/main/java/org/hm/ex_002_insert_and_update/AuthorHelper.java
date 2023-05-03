package org.hm.ex_002_insert_and_update;


import com.github.javafaker.Faker;
import org.hm.ex_002_insert_and_update.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Asus on 01.11.2017.
 */
public class AuthorHelper {

    private SessionFactory sessionFactory;

    public AuthorHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Author> getAuthorList(){
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();


        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaQuery cq = cb.createQuery(Author.class);

        Root<Author> root = cq.from(Author.class);// первостепенный, корневой entity (в sql запросе - from)


        cq.select(root);// необязательный оператор, если просто нужно получить все значения


         //этап выполнения запроса
        Query query = session.createQuery(cq);

        List<Author> authorList = query.getResultList();

        session.close();

        return authorList;
    }

    public Author getAuthorById(long id) {
        Session session = sessionFactory.openSession();
        Author author = session.get(Author.class, id); // получение объекта по id
        session.close();
        return author;
    }

    public Author addAuthor(Author author){

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Author author1 = session.get(Author.class, 6L);

        author1.setName("Mikola1");
        author1.setLastName("Gogol");

        session.save(author1);

         // сгенерит ID и вставит в объект

        session.getTransaction().commit();


        session.close();


        return author;

    }
    public void add200RandomAuthors() {
        Faker faker = new Faker();

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (int i = 1; i <= 200; i++) {

            String nameFaker = faker.name().firstName();
            String lastNameFaker = faker.name().lastName();
            Author newAuthor = new Author();

            newAuthor.setName(nameFaker);
            newAuthor.setLastName(lastNameFaker);
            session.save(newAuthor);

            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
        }
        session.getTransaction().commit();
        session.close();
    }
    public void updateAuthorNameById(long id, String newName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Author author = session.get(Author.class, id);
        if (author != null) {
            author.setName(newName);
            session.update(author);
        }

        session.getTransaction().commit();
        session.close();
    }

}
