package io.github.zbikp.lang;

import io.github.zbikp.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class LangRepository {
    //tworzymu Query zgodnie zdokumnetacja hiberata
    public List<Lang> findAll(){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var result =session.createQuery("from Lang",Lang.class).list();

        transaction.commit();
        session.close();
        return result;
    }
    //kolekcja modeli do kontaktu z DB

    public Optional<Lang> findById(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        var result =session.get(Lang.class, id);

        transaction.commit();
        session.close();
        return Optional.ofNullable(result);
    }
}
