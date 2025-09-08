package me.personal.myflix.entity.flix;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "missions")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class Mission extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 8599498736588744072L;

    @NotEmpty
    private String title;

    @NotEmpty
    private String grade;

    @NotEmpty
    private String description;

    @NotEmpty
    private String outcome;

    @ManyToOne
    @JoinColumn(name="objective_id", foreignKey=@ForeignKey(name = "FK_missions_TO_objectives"))
    @JsonIgnore
    private Objective objective;
}

