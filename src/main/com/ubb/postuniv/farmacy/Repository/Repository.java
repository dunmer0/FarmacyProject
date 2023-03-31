package main.com.ubb.postuniv.farmacy.Repository;

import main.com.ubb.postuniv.farmacy.Domain.DuplicateException;
import main.com.ubb.postuniv.farmacy.Domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository<T extends Entity> implements IRepository<T> {
    Map<Integer, T> repository = new HashMap<>();

    public Repository() {
    }

    @Override
    public void create(T t) throws DuplicateException {
        if (this.repository.containsKey(t.getId())) {
            throw new DuplicateException("ID is already in use");
        }
        this.repository.put(t.getId(), t);
    }

    @Override
    public List readAll() {
        return new ArrayList<>(this.repository.values());
    }

    @Override
    public T readOne(Integer id) {
        return this.repository.get(id);
    }

    @Override
    public void update(T t) throws DuplicateException {
        if (!this.repository.containsKey(t.getId())) {
            throw new DuplicateException("The id = " + t + "does not exist.");
        }
        this.repository.put(t.getId(), t);
    }

    @Override
    public void delete(Integer id) {
        this.repository.remove(id);
    }
}
