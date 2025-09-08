package me.personal.myflix.controller.base;

import jakarta.validation.Valid;
import me.personal.myflix.entity.base.BaseEntity;
import me.personal.myflix.exception.AppException;
import me.personal.myflix.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public abstract class BaseController<T extends BaseEntity<ID>, ID extends Serializable> implements IBaseController<T, ID> {

    private final BaseService<T, ID> baseService;
    private final String subClassName;

    protected BaseController(BaseService<T, ID> baseService) {
        this.baseService = baseService;
        this.subClassName = getClass().getSimpleName();
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<List<T>> get() {
        return ResponseEntity.ok(baseService.getBaseRepository().findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<T> getByID(@PathVariable("id") ID id) {
        return ResponseEntity.ok(
                baseService.getBaseRepository().findById(id).orElseThrow(
                        () -> new AppException(HttpStatus.NOT_FOUND, String.format("%s not found: %s", subClassName, id)))
        );
    }

    @Override
    @GetMapping("/paged")
    public ResponseEntity<List<T>> getAllPaged(@RequestParam int page, @RequestParam int size) {
        Page<T> pages = baseService.findAll(page, size);
        return ResponseEntity.ok(pages.getContent());
    }

    @Override
    @PostMapping("/")
    public ResponseEntity<T> post(@Valid @RequestBody T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(baseService.save(body));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<T> put(@PathVariable ID id, @Valid @RequestBody T body) {
        return ResponseEntity.ok(baseService.update(id, body));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        baseService.getBaseRepository().deleteById(id);
        baseService.getBaseRepository().flush();
        return ResponseEntity.noContent().build();
    }
}
