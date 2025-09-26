package me.personal.myflix.controller.base;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.List;

public interface IBaseController<T, ID extends Serializable> {
    ResponseEntity<List<T>> get();

    ResponseEntity<T> getByID(ID id);

    ResponseEntity<List<T>> getAllPaged(int page, int size);

    ResponseEntity<T> post(@RequestBody T body);

    ResponseEntity<T> put(@PathVariable ID id, @RequestBody T body);

    ResponseEntity<Void> delete(@PathVariable ID id);
}
