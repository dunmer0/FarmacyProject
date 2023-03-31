package main.com.ubb.postuniv.farmacy.Repository;


import main.com.ubb.postuniv.farmacy.Domain.DuplicateException;
import main.com.ubb.postuniv.farmacy.Domain.Entity;

import java.util.List;


public interface IRepository<T extends Entity> {



    void create(T t) throws DuplicateException;
    List<T> readAll();
    T readOne(Integer id);
    void update(T t) throws DuplicateException;
    void delete(Integer id);
}
