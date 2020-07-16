package test.demo.controller;

import com.wuyiccc.helloframework.core.annotation.Controller;
import com.wuyiccc.helloframework.injection.annotation.Autowired;
import com.wuyiccc.helloframework.mvc.annotation.RequestMapping;
import com.wuyiccc.helloframework.mvc.annotation.RequestParam;
import com.wuyiccc.helloframework.mvc.annotation.ResponseBody;
import com.wuyiccc.helloframework.mvc.type.ModelAndView;
import com.wuyiccc.helloframework.mvc.type.RequestMethod;
import test.demo.pojo.Book;
import test.demo.service.BookService;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/7/15 22:16
 * 岂曰无衣，与子同袍~
 */
@Controller
@RequestMapping(value = "/book")
public class BookController {


    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Book> getAllBooksInfo() {
       return bookService.getAllBooksInfo();
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addBook(
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "author") String author
    ){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("addSuccess.jsp").addViewData("bookName", bookName).addViewData("author", author);

        return modelAndView;
    }


}
