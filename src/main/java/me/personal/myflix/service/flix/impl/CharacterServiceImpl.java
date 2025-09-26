package me.personal.myflix.service.flix.impl;

import me.personal.myflix.entity.flix.Character;
import me.personal.myflix.repository.base.BaseRepository;
import me.personal.myflix.repository.flix.CharacterRepository;
import me.personal.myflix.service.base.impl.BaseServiceImpl;
import me.personal.myflix.service.flix.CharacterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterServiceImpl extends BaseServiceImpl<Character, Long> implements CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(BaseRepository<Character, Long> baseRepository, CharacterRepository characterRepository) {
        super(baseRepository, Character.class);
        this.characterRepository = characterRepository;
    }

    @Override
    @Transactional
    public Character patch(Character newCharacter, Character oldCharacter) {
        newCharacter.setFullName(oldCharacter.getFullName());
        newCharacter.setTypology(oldCharacter.getTypology());
        return newCharacter;
    }

}
