package lv.javaguru.java2.ishop.database;

import lv.javaguru.java2.ishop.domain.Producer;

import java.util.List;

/**
 * Created by Ann on 05/02/14.
 */
public interface ProducerDAO
{

    void create(Producer producer) throws DBException;

    Producer getById(Long id) throws DBException;

    Producer getByName(String name) throws DBException;

    List<Producer> getAll() throws DBException;

    void delete(Long id) throws DBException;

    void update(Producer producer) throws DBException;
}
