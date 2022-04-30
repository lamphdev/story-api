package lamph.web.storyapi.service;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpaceService {

    private final String BUCKET_NAME = "media-image";
    private final AmazonS3 s3Client;
    private final Scheduler scheduler;

    @PostConstruct
    public void init() {
        String resource = "arn:aws:s3:::" + BUCKET_NAME + "/*";
        Statement statement = new Statement(Statement.Effect.Allow)
                .withActions(S3Actions.GetObject)
                .withPrincipals(Principal.All)
                .withResources(new Resource(resource));

        Policy policy = new Policy();
        policy.setStatements(Collections.singleton(statement));
        String policyJson = policy.toJson();
        log.info("apply policy {} for bucket {}", policyJson, BUCKET_NAME);
        s3Client.setBucketPolicy(BUCKET_NAME, policyJson);
    }

    public Mono<String> upload(FilePart file) {
        ObjectMetadata metadata = new ObjectMetadata();
        String fileIdentify = UUID.randomUUID().toString();
        return Mono.fromCallable(() -> {
            InputStream is = inputStreamFormFilePart(file);
            s3Client.putObject(BUCKET_NAME, fileIdentify, is, metadata);
            return fileIdentify;
        }).publishOn(scheduler);
    }

    public InputStream inputStreamFormFilePart(FilePart filePart) throws IOException {
        PipedOutputStream osPipe = new PipedOutputStream();
        PipedInputStream isPipe = new PipedInputStream(osPipe);

        DataBufferUtils.write(filePart.content(), osPipe)
                .subscribeOn(Schedulers.elastic())
                .doOnComplete(() -> {
                    try {
                        osPipe.close();
                    } catch (IOException ignored) {
                    }
                })
                .subscribe(DataBufferUtils.releaseConsumer());
        return isPipe;
    }

}
