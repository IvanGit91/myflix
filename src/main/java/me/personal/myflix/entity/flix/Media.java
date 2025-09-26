package me.personal.myflix.entity.flix;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.personal.myflix.entity.base.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "media")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"characters"}, callSuper = false)
@ToString(exclude = {"characters"})
public class Media extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 3513205756875180593L;

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "genre is mandatory")
    private String genre;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "media_type_id", foreignKey = @ForeignKey(name = "FK_media_TO_media_types"))
    private MediaType mediaType;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Character> characters = new ArrayList<>();
}

