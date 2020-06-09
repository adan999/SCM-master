package com.scm.scm.Repository;

import java.awt.print.Pageable;
import java.util.List;

public interface BaseRepo<T> {

    public boolean save(T object);

    public boolean update(T object);

    public List<T> findAll();

    public T findById(int Id);
}
