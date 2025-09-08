package me.personal.myflix.controller.flix;

import me.personal.myflix.controller.base.BaseController;
import me.personal.myflix.entity.flix.Media;
import me.personal.myflix.service.flix.MediaService;
import me.personal.myflix.service.base.BaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/media")
public class MediaController extends BaseController<Media, Long> {

    private final MediaService mediaService;

    public MediaController(BaseService<Media, Long> baseService, MediaService mediaService) {
        super(baseService);
        this.mediaService = mediaService;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<Media> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(mediaService.findByTitle(title));
    }
}
