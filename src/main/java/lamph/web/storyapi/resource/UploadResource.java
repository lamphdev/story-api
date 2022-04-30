package lamph.web.storyapi.resource;

import lamph.web.storyapi.resource.response.ImgBbUploadResponse;
import lamph.web.storyapi.service.SpaceService;
import lamph.web.storyapi.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class UploadResource {

    private final UploadService uploadService;
    private final SpaceService spaceService;

    @PostMapping
    public Mono<ImgBbUploadResponse> upload(@RequestPart("image") FilePart image) {
        return uploadService.upload(image);
    }

    @PostMapping(value = "/space",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<String> uploadSpace(@RequestPart("file") FilePart file) {
        return spaceService.upload(file);
    }

}
