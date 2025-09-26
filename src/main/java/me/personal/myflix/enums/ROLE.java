package me.personal.myflix.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.personal.myflix.annotation.EnumExposedViaRest;

@EnumExposedViaRest(enumApiName = "mediaType", multilanguageDescription = false)
@Getter
@AllArgsConstructor
public enum ROLE {
    ROLE_MANAGER,
    ROLE_CUSTOMER,
    ROLE_EMPLOYEE
}
