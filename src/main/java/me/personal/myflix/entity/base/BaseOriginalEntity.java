package me.personal.myflix.entity.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class BaseOriginalEntity<T, ID extends Serializable> extends BaseEntity<ID> {

    @Transient
    @JsonIgnore
    @Getter
    private T originalObj;

    @PostLoad
    public void onLoad() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String serialized = mapper.writeValueAsString(this);
            this.originalObj = (T) mapper.readValue(serialized, this.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}