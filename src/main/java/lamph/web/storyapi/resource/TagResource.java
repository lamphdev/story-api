package lamph.web.storyapi.resource;

import lamph.web.storyapi.resource.request.tag.CreateTagRequest;
import lamph.web.storyapi.resource.request.tag.UpdateTagRequest;
import lamph.web.storyapi.service.TagService;
import lamph.web.storyapi.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagResource {

    private final TagService tagService;

    @GetMapping
    public Flux<Tag> getList() {
        return tagService.findAll();
    }

    @PostMapping
    public Mono<Tag> create(@Valid @RequestBody CreateTagRequest request) {
        return tagService.create(request);
    }


    @PutMapping("/{id}")
    public Mono<Tag> update(@NotEmpty @Length(max = 100) @PathVariable String id,
                            @Valid @RequestBody UpdateTagRequest request) {
        return tagService.update(id, request);
    }


}
