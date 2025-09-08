package me.personal.myflix.entity.flix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.personal.myflix.entity.base.BaseEntity;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "powers")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Power extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7201675252925273334L;

    @NotEmpty
    private String name;

    @NotEmpty
    private String effect;

    @ManyToOne
    @JoinColumn(name="character_id", foreignKey=@ForeignKey(name = "FK_powers_TO_characters"))
    @JsonIgnoreProperties({"powers", "linkedCharacters", "objectives"}) // Solve the infinite loop in the json
    private Character character;
}

