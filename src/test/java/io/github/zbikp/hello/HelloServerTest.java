package io.github.zbikp.hello;

import io.github.zbikp.hello.HelloService;
import io.github.zbikp.lang.Lang;
import io.github.zbikp.lang.LangRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HelloServerTest {
private static final String WELCOME ="Hello";
    private static final String FALLBACK_ID_WELCOME = "Hola";
//TDD najpierw testy potem kod
    @Test
      public void test_null_prepareGreeting_returnsFallbackName() throws Exception {
        //given
        var mockRepository = alwaysReturnHelloRepository();
        var SUT = new HelloService(mockRepository);
        //when
        var result = SUT.prepareGreeting(null, "-1");
        //then
        assertEquals(WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }
    @Test
    public void test_prepareGreeting_nullLang_returnsGreetingWithFallbackIdLang() throws Exception {
        //given

        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);
        String name = "test";
        //when
        var result = SUT.prepareGreeting(null, null);
        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }
    @Test
    public void test_prepareGreeting_nonExistingLang_returnsGreetingWithFallbackLang() throws Exception {
        //given

        var mockRepository = new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockRepository);

        //when
        var result = SUT.prepareGreeting(null, "-1");
        //then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!", result);
    }
    @Test
    public void test_prepareGreeting_textLang_returnsGreetingWithFallbackIdLang() throws Exception {
        //given

        var mockRepository = fallbackLangIdRepository();
        var SUT = new HelloService(mockRepository);
        String name = "test";
        //when
        var result = SUT.prepareGreeting(null, "abc");
        //then
        assertEquals(FALLBACK_ID_WELCOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    private LangRepository fallbackLangIdRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                if (id.equals(HelloService.FALLBACK_LANG.getId())) {
                    return Optional.of(new Lang(null, FALLBACK_ID_WELCOME, null));
                }
                return Optional.empty();
            }
        };
    }


    @Test
    public void test_name_prepareGreeting_returnsGreetingWithName() throws Exception {
        //given
        String name = "test";
        var mockRepository = alwaysReturnHelloRepository();
        var SUT = new HelloService(mockRepository);
        //when
        var result = SUT.prepareGreeting(name, "-1");
        //then
        assertEquals(WELCOME + " "  + name + "!", result);
    }
    private LangRepository alwaysReturnHelloRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null,WELCOME, null));
            }
        };
    }
}
