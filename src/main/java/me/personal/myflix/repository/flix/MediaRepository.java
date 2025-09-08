package me.personal.myflix.repository.flix;


import me.personal.myflix.entity.flix.Media;
import me.personal.myflix.repository.base.BaseRepository;

public interface MediaRepository extends BaseRepository<Media, Long> {
    Media findByTitle(String title);
}
