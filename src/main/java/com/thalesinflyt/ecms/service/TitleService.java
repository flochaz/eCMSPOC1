package com.thalesinflyt.ecms.service;

import com.thalesinflyt.ecms.domain.Title;
import com.thalesinflyt.ecms.repository.TitleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Title.
 */
@Service
public class TitleService {

    private final Logger log = LoggerFactory.getLogger(TitleService.class);
    
    @Inject
    private TitleRepository titleRepository;
    
    /**
     * Save a title.
     * @return the persisted entity
     */
    public Title save(Title title) {
        log.debug("Request to save Title : {}", title);
        Title result = titleRepository.save(title);
        return result;
    }

    /**
     * Save a bulk of titles.
     * @return the persisted entities
     */
    public List<Title> save(List<Title> titles) {
        log.debug("Request to save {} Titles", titles.size());
        List<Title> result = titleRepository.save(titles);
        return result;
    }
    
    /**
     *  get all the titles.
     *  @return the list of entities
     */
    public List<Title> findAll() {
        log.debug("Request to get all Titles");
        List<Title> result = titleRepository.findAll();
        return result;
    }

    /**
     *  get one title by id.
     *  @return the entity
     */
    public Title findOne(String id) {
        log.debug("Request to get Title : {}", id);
        Title title = titleRepository.findOne(id);
        return title;
    }

    /**
     *  delete the  title by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Title : {}", id);
        titleRepository.delete(id);
    }
    
    /**
     *  delete all the titles.
     */
    public void deleteAll() {
        log.debug("Request to delete all Titles");
        titleRepository.deleteAll();
    }
}
