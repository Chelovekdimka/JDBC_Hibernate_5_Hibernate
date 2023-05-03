package org.hm.ex_002_insert_and_update;

import com.github.javafaker.Faker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hm.ex_002_insert_and_update.entity.Author;

//    Из пакета ex_002_insert_and_update создайте в цикле 200 объектов author
//    и сохраните в БД. Значения полей могут быть любыми.
//    Используйте метод flush для каждых 10 объектов. Метод сommit выполняйте один раз в конце.

//    В классе BookHelper создать метод, который получает название книг и имя автора.

//    К дополнительному заданию добавить метод обновления имени автора по id.
//    (То, что было на уроке, только реализовать это правильно).
//    Аналогично сделать и в классе BookHelper с предыдущего ДЗ.

public class Main {

 //   private static final Logger LOG = Logger.getLogger(AuthorHelper.class.getName());

    public static void main(String[] args) {

        AuthorHelper ah = new AuthorHelper();
//        розкоментувати для перевірки
//        ah.add200RandomAuthors();
        BookHelper bh = new BookHelper();
        bh.printBookAndAuthorDetails();
        ah.updateAuthorNameById(4, "Metro");
    }

}
