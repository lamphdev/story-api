package lamph.web.storyapi.resource;

import lamph.web.storyapi.domain.Story;
import lamph.web.storyapi.resource.request.PageRequest;
import lamph.web.storyapi.resource.request.story.CreateStoryRequest;
import lamph.web.storyapi.resource.request.story.UpdateStoryRequest;
import lamph.web.storyapi.resource.response.PageResponse;
import lamph.web.storyapi.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stories")
public class StoryResource {

    private final StoryService storyService;

    @PostMapping
    public Mono<Story> create(@Valid @RequestBody CreateStoryRequest request) {
        return storyService.createStory(request);
    }

    @PutMapping("/{id}")
    public Mono<Story> update(@PathVariable Long id,
                              @Valid @RequestBody UpdateStoryRequest request) {
        return storyService.updateStory(id, request);
    }

    @GetMapping
    public Mono<PageResponse<Story>> getPage(@ModelAttribute PageRequest request) {
        return storyService.getPage(request).map(PageResponse::new);
    }

}
