package io.github.zbikp.lang;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Lang",urlPatterns = {"/api/langs"})
public class LangServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(LangServlet.class);

    private LangService service;
    //poniżej mapper dołożone dependency żeby w doGet można było zamienić na format json
    private ObjectMapper mapper;

    //Servlet container needs it
   //konstruktor dla servletu
    @SuppressWarnings("unused")
    public LangServlet(){

        this(new LangService(), new ObjectMapper());
    }
    //konstruktor
    LangServlet(LangService service, ObjectMapper mapper){
        this.mapper = mapper;
        this.service = service;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Got request with paramiters"+req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        service.findAll(); // musimy zmapować obiekt mapy na json
        mapper.writeValue(resp.getOutputStream(),service.findAll()); //strumień odpowiedzi mapujemy na json
    }
}
