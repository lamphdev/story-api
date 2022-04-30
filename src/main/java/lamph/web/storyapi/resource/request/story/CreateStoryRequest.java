package lamph.web.storyapi.resource.request.story;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.codec.multipart.FilePart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateStoryRequest {

    @NotEmpty
    @Length(max = 300)
    private String name;

    @NotEmpty
    @Length(max = 200)
    private String ref1;

    @NotNull
    private Long ref2;

    @Length(max = 4000)
    private String description;

    @NotNull
    private Integer complete;

    @Length(max = 250)
    private String author;

    @NotNull
    private Integer status;

    private List<String> tags;

    private FilePart thumbnail;

}
