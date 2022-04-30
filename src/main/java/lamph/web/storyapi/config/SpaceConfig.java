package lamph.web.storyapi.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpaceConfig {

    private final SpaceProperties spaceProperties;

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(
                spaceProperties.getClientId(), spaceProperties.getSecretId());
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        spaceProperties.getEndpoint(),"sgp1"
                )).build();
        return client;
    }

}
