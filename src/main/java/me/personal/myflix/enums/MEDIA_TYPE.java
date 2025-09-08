package me.personal.myflix.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import me.personal.myflix.annotation.EnumExposedViaRest;

@EnumExposedViaRest(enumApiName = "mediaType", multilanguageDescription = false)
@Getter
@AllArgsConstructor
public enum MEDIA_TYPE {
	MOVIE,
	SERIE,
    ANIME,
    DOCUMENTARY
}
