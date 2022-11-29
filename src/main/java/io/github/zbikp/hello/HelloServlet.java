package io.github.zbikp.hello;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "Hello",urlPatterns = {"/api"})
public class HelloServlet extends HttpServlet {
    private static final String NAME_PARAM = "name";
    private static final String LANG_PARAM = "lang";
    private final Logger logger = LoggerFactory.getLogger(HelloServlet.class);

    private HelloService service;

    //Servlet container needs it
    @SuppressWarnings("unused")
   //konstruktor dla servletu
    public HelloServlet(){

        this(new HelloService());
    }
    //konstruktor
    HelloServlet(HelloService service){

        this.service = service;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with paramiters"+req.getParameterMap()+"got");
        var name = req.getParameter(NAME_PARAM);
        var lang = req.getParameter(LANG_PARAM);
        Integer langId = null;

        try {
            langId = Integer.valueOf(lang);
        } catch (NumberFormatException e) {
            logger.warn("Non-numeric language id used: "+ lang);
        }
        resp.getWriter().write(service.prepareGreeting(name, langId));
    }
}
