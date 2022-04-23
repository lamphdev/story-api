package lamph.web.storyapi.service;

import lamph.web.storyapi.resource.request.tag.CreateTagRequest;
import lamph.web.storyapi.resource.request.tag.UpdateTagRequest;
import lamph.web.storyapi.domain.Tag;
import lamph.web.storyapi.repository.TagRepository;
import lamph.web.storyapi.utils.IdUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final Scheduler scheduler;

    public Flux<Tag> findAll() {
        return Flux.fromStream(() -> tagRepository.findAll().stream())
                .publishOn(scheduler);
    }

    /**
     * create new tag
     *
     * @param request request payload
     * @return saved tag
     */
    public Mono<Tag> create(CreateTagRequest request) {
        String id = IdUtils.fromString(request.getName());
        return findOne(id)
                .<Tag>flatMap(tag -> Mono.error(new RuntimeException("Existed")))
                .switchIfEmpty(Mono.just(new Tag(request)))
                .flatMap(tag -> Mono.fromCallable(() -> tagRepository.save(tag)))
                .publishOn(scheduler);
    }

    /**
     * update existed tag
     *
     * @param id      id of tag
     * @param request request payload
     * @return updated tag
     */
    public Mono<Tag> update(String id, UpdateTagRequest request) {
        return findOne(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Not Found")))
                .map(tag -> tag.updateData(request))
                .flatMap(tag -> Mono.fromCallable(() -> tagRepository.save(tag)))
                .publishOn(scheduler);
    }

    /**
     * find tag by id
     *
     * @param id id of tag
     * @return tag
     */
    public Mono<Tag> findOne(String id) {
        return Mono.fromCallable(() -> tagRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .publishOn(scheduler);
    }

}
