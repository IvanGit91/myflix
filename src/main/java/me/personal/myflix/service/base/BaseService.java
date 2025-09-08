package me.personal.myflix.service.base;

import me.personal.myflix.entity.base.BaseEntity;
import me.personal.myflix.repository.base.BaseRepository;
import org.springframework.data.domain.Page;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> /*extends JpaRepository<T,ID>*/ {
    T save(T entity);
    boolean saveLists(T entity);
    boolean saveListsSingle(T entity, ID id);
    boolean saveListsMulti(T entity, ID id);
    ID saveUpdateListsMultiRecursive(T entity, String prevMappedBy);
    T update(T entity);
    T update(ID id, T entity);
    boolean updateListsRecursive(T entity, T oldEntity, String mappedBy, boolean recursive);
    T patch(T entity, String...fields);
    T patch(ID id, T entity, String...fields);
    T patch(T newEntity, T oldEntity);
    T patch(T newEntity, T oldEntity, String[] fields);
    T findByNaturalId(Object naturalId);
    Page<T> findAll(int page, int size);
    // BaseRepository extends the JPA repository, so it has all the methods of JpaRepository
    BaseRepository<T,ID> getBaseRepository();
}
