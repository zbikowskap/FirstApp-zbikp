package io.github.zbikp.hello;

import io.github.zbikp.lang.Lang;
import io.github.zbikp.lang.LangRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

class HelloService {
    static final String FALLBACK_NAME = "world";
    static final Lang FALLBACK_LANG = new Lang(1, "Hello", "EN");
    private final Logger logger = LoggerFactory.getLogger(HelloService.class);
    private LangRepository repository;

    HelloService() {
        this(new LangRepository());
    }

    HelloService(LangRepository repository) {
        this.repository = repository;
    }

    //   String prepareGreeting(String name){
//       return prepareGreeting(name,null);
//   }
    String prepareGreeting(String name, String lang) {
        Integer langID;
        try {
            langID = Optional.ofNullable(lang).map(Integer::valueOf).orElse(FALLBACK_LANG.getId());
        } catch (NumberFormatException e) {
            logger.warn("Non-numeric language id used: "+ lang);
            langID = FALLBACK_LANG.getId();
        }
        var welcomeMsg = repository.findById(langID).orElse(FALLBACK_LANG).getWelcomeMsg();
        var nameToWelcome = Optional.ofNullable(name).orElse(FALLBACK_NAME);
        return welcomeMsg + " " + nameToWelcome + "!";
    }
}
