package me.personal.myflix.repository.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, BaseRepositoryCustom<T, ID> {
    // COUNT
    @Query("select count(e) from #{#entityName} e")
    Long countAll();

    Page<T> findAll(Pageable pageable);

    @Query("select e from  #{#entityName} e")
    List<T> findAll(Specification<T> specification);

    List<T> findAll(Specification<T> specification, Sort sort);

    Page<T> findAll(Specification<T> specification, Pageable pageable);
}
