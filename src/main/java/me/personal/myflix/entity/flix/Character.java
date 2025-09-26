package me.personal.myflix.entity.flix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import me.personal.myflix.annotation.Recursive;
import me.personal.myflix.entity.base.BaseOriginalEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "characters")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"linkedCharacters", "powers", "objectives"})
@ToString(callSuper = true, exclude = {"linkedCharacters", "powers", "objectives"})
public class Character extends BaseOriginalEntity<Character, Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 3854999379449901523L;

    @NotEmpty
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotEmpty
    private String typology;

    @ManyToOne
    @JoinColumn(name = "media_id", foreignKey = @ForeignKey(name = "FK_characters_TO_media"))
    @JsonIgnoreProperties("characters") // Solve the infinite loop in the json
    private Media media;

    @ManyToOne
    @JoinColumn(name = "character_id", foreignKey = @ForeignKey(name = "FK_characters_TO_character"))
    @JsonIgnoreProperties({"character", "linkedCharacters", "powers", "objectives"})
    // Solve the infinite loop in the json
    private Character parentCharacter;

    @OneToMany(mappedBy = "parentCharacter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Recursive
    private List<Character> linkedCharacters = new ArrayList<>();

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Power> powers = new ArrayList<>();

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Objective> objectives = new ArrayList<>();

    public Character(Long id, String fullName, String typology) {
        this.id = id;
        this.fullName = fullName;
        this.typology = typology;
    }
}

