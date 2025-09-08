package me.personal.myflix.entity.flix;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.personal.myflix.entity.base.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "places")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Place extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 842470462723106557L;

    @NotEmpty
    private String name;
}

