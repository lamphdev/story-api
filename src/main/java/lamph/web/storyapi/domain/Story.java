package lamph.web.storyapi.domain;

import lamph.web.storyapi.resource.request.story.CreateStoryRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "story")
@NoArgsConstructor
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Story_SEQ")
    @SequenceGenerator(name = "Story_SEQ", sequenceName = "Story_SEQ")
    private Long id;

    @Column(length = 300, nullable = false, unique = true)
    private String name;

    @Column(length = 300)
    private String thumbnail;

    @Column(length = 4000)
    private String description;

    @Column(nullable = false)
    private Integer complete;

    @Column(length = 100)
    private String author;

    private Instant createdDate;

    private Instant updatedDate;

    private Integer status;

    public Story(CreateStoryRequest request) {

        name = request.getName();
        description =request.getDescription();
        complete = request.getComplete();
        author = request.getAuthor();
        createdDate = Instant.now();
        updatedDate = Instant.now();
        status = request.getStatus();
    }

    public Story(CreateStoryRequest request, String thumbnail) {
        this(request);
        this.thumbnail = thumbnail;
    }

}
