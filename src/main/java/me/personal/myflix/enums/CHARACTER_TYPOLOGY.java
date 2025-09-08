package me.personal.myflix.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.personal.myflix.annotation.EnumExposedViaRest;

@EnumExposedViaRest(enumApiName = "characterTypology", multilanguageDescription = false)
@Getter
@AllArgsConstructor
public enum CHARACTER_TYPOLOGY {
	GOOD,
	BAD
}
