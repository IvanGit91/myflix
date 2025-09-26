package me.personal.myflix.service.flix.impl;

import me.personal.myflix.entity.flix.Character;
import me.personal.myflix.entity.flix.Media;
import me.personal.myflix.repository.base.BaseRepository;
import me.personal.myflix.repository.flix.MediaRepository;
import me.personal.myflix.service.base.impl.BaseServiceImpl;
import me.personal.myflix.service.flix.MediaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MediaServiceImpl extends BaseServiceImpl<Media, Long> implements MediaService {
    private final MediaRepository mediaRepository;

    public MediaServiceImpl(BaseRepository<Media, Long> baseRepository, MediaRepository mediaRepository) {
        super(baseRepository, Media.class);
        this.mediaRepository = mediaRepository;
    }

    @Override
    public Media findByTitle(String title) {
        return mediaRepository.findByTitle(title);
    }

    @Override
    public boolean saveLists(Media media) {
        boolean resave = false;
        List<Character> personaggi = media.getCharacters();
        if (media.getId() == null) {
            media.setCharacters(null);
            getBaseRepository().save(media);
        }
        if (personaggi != null && !personaggi.isEmpty()) {
            personaggi.forEach(p -> p.setMedia(media));
            media.setCharacters(personaggi);
            resave = true;
        }
        return resave;
    }

    @Override
    @Transactional
    public Media patch(Media newMedia, Media oldMedia) {
        newMedia.setTitle(oldMedia.getTitle());
        newMedia.setGenre(oldMedia.getGenre());
        return newMedia;
    }

}
