package me.personal.myflix.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.personal.myflix.entity.base.BaseEntity;
import org.hibernate.annotations.NaturalId;


import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3327823368576560654L;

    @NaturalId
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 3, message = "Length must be more than 3")
    private String password;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String address;

    @NotNull
    private Boolean active;

    @NotEmpty
    private String role = "ROLE_CUSTOMER";
}

