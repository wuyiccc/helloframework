package test.demo.service;

import com.wuyiccc.helloframework.core.annotation.Service;
import test.demo.pojo.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/7/15 22:18
 * 岂曰无衣，与子同袍~
 */

@Service
public class BookService {


    public List<Book> getAllBooksInfo() {

        List<Book> booksInfo = new ArrayList<>();
        booksInfo.add(new Book("book1", "wuyiccc1"));
        booksInfo.add(new Book("book2", "wuyiccc2"));

        return booksInfo;
    }

}
