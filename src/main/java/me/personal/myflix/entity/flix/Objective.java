package me.personal.myflix.entity.flix;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "objectives")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"missions"}, callSuper = true)
@ToString(exclude = {"missions"})
public class Objective extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7423902257210636380L;

    @NotEmpty
    private String title;

    @NotEmpty
    private String grade;

    @NotEmpty
    private String description;

    @NotEmpty
    private String outcome;

    @ManyToOne
    @JoinColumn(name = "character_id", foreignKey = @ForeignKey(name = "FK_objectives_TO_characters"))
    @JsonIgnore
    private Character character;

    @OneToMany(mappedBy = "objective", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mission> missions = new ArrayList<>();

}

