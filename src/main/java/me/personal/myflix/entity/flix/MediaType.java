package me.personal.myflix.entity.flix;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.personal.myflix.entity.base.BaseEntity;
import me.personal.myflix.enums.MEDIA_TYPE;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "media_types")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class MediaType extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Enumerated(EnumType.STRING)
    private MEDIA_TYPE name;

    @NotEmpty
    @Column(name = "display_name", nullable = false)
    private String displayName;
}

