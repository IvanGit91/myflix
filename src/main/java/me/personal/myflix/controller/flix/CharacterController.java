package me.personal.myflix.controller.flix;

import me.personal.myflix.controller.base.BaseController;
import me.personal.myflix.entity.flix.Character;
import me.personal.myflix.service.flix.CharacterService;
import me.personal.myflix.service.base.BaseService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/character")
public class CharacterController extends BaseController<Character, Long> {

    private final CharacterService characterService;

    public CharacterController(BaseService<Character, Long> baseService, CharacterService characterService) {
        super(baseService);
        this.characterService = characterService;
    }
}
