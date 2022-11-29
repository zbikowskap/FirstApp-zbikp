package io.github.zbikp.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.zbikp.lang.LangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Todo",urlPatterns = {"/api/todos/*"})
public class TodoServlet  extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(io.github.zbikp.lang.LangServlet.class);

    private TodoRepository repository;
    //poniżej mapper dołożone dependency żeby w doGet można było zamienić na format json
    private ObjectMapper mapper;

    //Servlet container needs it
    //konstruktor dla servletu
    @SuppressWarnings("unused")
    public TodoServlet() {

        this(new TodoRepository(), new ObjectMapper());
    }

    //konstruktor
    TodoServlet(TodoRepository repository, ObjectMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with paramiters" + req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");

        mapper.writeValue(resp.getOutputStream(), repository.findAll()); //strumień odpowiedzi mapujemy na json
    }

    //nadpisuje metodę doPut i korzystam z http req.getPathInfo
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var pathInfo = req.getPathInfo();
        try {
            var todoId = Integer.valueOf(pathInfo.substring(1)); //tu też pozbywam się ze stringa / substringiem
            var todo = repository.toggleTodo(todoId);
            resp.setContentType("application/json;charset=UTF-8");

            mapper.writeValue(resp.getOutputStream(), todo); //strumień odpowiedzi mapujemy na json
        } catch (NumberFormatException e) {
            logger.warn("Wrong path used" + pathInfo);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      var newTodo =   mapper.readValue(req.getInputStream(), Todo.class); // to pobieramy wpisane nowe todo
        mapper.writeValue(resp.getOutputStream(), repository.addTodo(newTodo)); //wysyłam je do repo i dodaje
    }
}
