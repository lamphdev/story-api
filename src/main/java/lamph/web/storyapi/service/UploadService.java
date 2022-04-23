package lamph.web.storyapi.service;

import lamph.web.storyapi.config.Constant;
import lamph.web.storyapi.resource.response.ImgBbUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final WebClient.Builder clientBuilder;

    public Mono<ImgBbUploadResponse> upload(FilePart filePart) {
        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("image", filePart);

        BodyInserters.FormInserter bodyInserters = BodyInserters.fromMultipartData(bodyBuilder.build())
                .with("name", filePart.filename())
                .with("key", "d90b4bd14102d847b01b343497e20fd8");

        return clientBuilder.build()
                .post()
                .uri(Constant.UPLOAD_PROVIDER)
                .body(bodyInserters)
                .retrieve()
                .bodyToMono(ImgBbUploadResponse.class);
    }

}
