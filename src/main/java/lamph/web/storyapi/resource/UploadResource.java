package lamph.web.storyapi.resource;

import lamph.web.storyapi.resource.response.ImgBbUploadResponse;
import lamph.web.storyapi.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadResource {

    private final UploadService uploadService;

    @PostMapping
    public Mono<ImgBbUploadResponse> upload(@RequestPart("image") FilePart image) {
        return uploadService.upload(image);
    }

}
