package dao;

import java.util.List;

public interface CrudDAO <T>
{
    T get(T model);
    List<T> getAll();
    void create(T model);
    void update(T model);
    void delete(T model);
    void deleteAll();
}
