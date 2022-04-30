package lamph.web.storyapi.service;

import lamph.web.storyapi.domain.Story;
import lamph.web.storyapi.domain.StoryMapTag;
import lamph.web.storyapi.repository.StoryMapTagRepository;
import lamph.web.storyapi.repository.StoryRepository;
import lamph.web.storyapi.repository.TagRepository;
import lamph.web.storyapi.resource.request.PageRequest;
import lamph.web.storyapi.resource.request.story.CreateStoryRequest;
import lamph.web.storyapi.resource.request.story.UpdateStoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryMapTagRepository storyMapTagRepository;
    private final StoryRepository storyRepository;
    private final TagRepository tagRepository;
    private final UploadService uploadService;
    private final Scheduler scheduler;

    public Mono<Story> createStory(CreateStoryRequest request) {
        return uploadService.upload(request.getThumbnail())
                .map(res -> res.getData().getThumb().getUrl())
                .map(thumbnail -> new Story(request, thumbnail))
                .flatMap(story -> Mono.fromCallable(
                        () -> saveTagForStory(story, request.getTags())).publishOn(scheduler)
                );
    }

    public Mono<Story> updateStory(Long id, UpdateStoryRequest request) {
        return findById(id).switchIfEmpty(Mono.error(new RuntimeException("Not Found")))
                .flatMap(story -> uploadService.upload(request.getThumbnail())
                        .flatMap(uploadResult -> {
                                    story.updateField(request, uploadResult.getData().getThumb().getUrl());
                                    return Mono.fromCallable(
                                            () -> saveTagForStory(story, request.getTags())
                                    ).publishOn(scheduler);
                                }
                        )
                );
    }

    public Mono<Page<Story>> getPage(PageRequest request) {
        return Mono.fromCallable(() -> storyRepository.findAll(request.getPageable()))
                .publishOn(scheduler);
    }

    public Mono<Story> findById(Long id) {
        return Mono.fromCallable(() -> storyRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .publishOn(scheduler);
    }

    private Story saveTagForStory(Story story, List<String> tags) {
        storyRepository.save(story);
        storyMapTagRepository.deleteAllByStoryId(story.getId());
        List<StoryMapTag> listToSave = tags.stream()
                .map(tagRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(tag -> new StoryMapTag(story, tag))
                .collect(Collectors.toList());
        storyMapTagRepository.saveAll(listToSave);
        return story;
    }

}
