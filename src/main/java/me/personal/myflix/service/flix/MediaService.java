package me.personal.myflix.service.flix;


import me.personal.myflix.entity.flix.Media;
import me.personal.myflix.service.base.BaseService;

public interface MediaService extends BaseService<Media, Long> {
    Media findByTitle(String title);
}
