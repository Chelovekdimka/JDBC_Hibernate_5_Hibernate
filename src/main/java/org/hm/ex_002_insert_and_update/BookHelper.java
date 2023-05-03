package org.hm.ex_002_insert_and_update;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hm.ex_002_insert_and_update.entity.Author;
import org.hm.ex_002_insert_and_update.entity.Book;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BookHelper {
    private SessionFactory sessionFactory;

    public BookHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Book> getBookList(){
        // открыть сессию - для манипуляции с персист. объектами
        Session session = sessionFactory.openSession();


        // этап подготовки запроса

        // объект-конструктор запросов для Criteria API
        CriteriaBuilder cb = session.getCriteriaBuilder();// не использовать session.createCriteria, т.к. deprecated

        CriteriaQuery cq = cb.createQuery(Author.class);

        Root<Book> root = cq.from(Book.class);// первостепенный, корневой entity (в sql запросе - from)


        cq.select(root);// необязательный оператор, если просто нужно получить все значения


        //этап выполнения запроса
        Query query = session.createQuery(cq);

        List<Book> bookList = query.getResultList();

        session.close();

        return bookList;
    }

    public Book getBookById(long id) {
        Session session = sessionFactory.openSession();
        Book book = session.get(Book.class, id); // получение объекта по id
        session.close();
        return book;
    }

    public Book addAuthor(Book book){

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        Book book1 = session.get(Book.class, 6L);

        book1.setName(" ");

        session.save(book1);

        // сгенерит ID и вставит в объект

        session.getTransaction().commit();


        session.close();


        return book1;

    }
    public void printBookAndAuthorDetails() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Получаем список всех книг
        List<Book> bookList = getBookList();

        for (Book book : bookList) {
            // Получаем автора книги
            Author author = book.getAuthor();

            // Выводим название книги и имя автора
            System.out.println("Название книги: " + book.getName());
            System.out.println("Имя автора: " + author.getName());
            System.out.println("---------------------------------");
        }

        session.getTransaction().commit();
        session.close();
    }
    public void updateBookNameById(long id, String newName) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Book book = session.get(Book.class, id);
        if (book != null) {
            book.setName(newName);
            session.update(book);
        }

        session.getTransaction().commit();
        session.close();
    }
}
