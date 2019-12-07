package dao;

import java.util.List;

public interface CrudDAO <T>
{
    void create(T model);
    T retrieve(T model);
    List<T> retrieveAll();
    void update(T model);
    void delete(T model);
    void deleteAll();
}
