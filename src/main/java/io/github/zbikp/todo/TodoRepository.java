package io.github.zbikp.todo;

import io.github.zbikp.HibernateUtil;

import java.util.List;

public class TodoRepository {

        //tworzymu Query zgodnie z dokumnetacja hiberata
        List<Todo> findAll(){
            var session = HibernateUtil.getSessionFactory().openSession();
            var transaction = session.beginTransaction();
            var result =session.createQuery("from Todo",Todo.class).list();

            transaction.commit();
            session.close();
            return result;
        }
        //metoda do zmiany zadania zrobione nie zrobione, trzeba otworzyśc sesję na put
        Todo toggleTodo(Integer id){
            var session = HibernateUtil.getSessionFactory().openSession();
            var transaction = session.beginTransaction();

            var result =session.get(Todo.class,id);
            result.setDone(!result.isDone()); //odwracam wartość zadania zrobione nie zrobione

            transaction.commit();
            session.close();
            return result;
        }
    Todo addTodo(Todo newTodo){
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        session.persist(newTodo); //persist nic nie zwraca tylko zapisuje zmiany
        transaction.commit();
        session.close();
        return newTodo;
    }

    }

