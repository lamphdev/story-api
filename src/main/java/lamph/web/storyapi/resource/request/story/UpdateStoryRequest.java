package lamph.web.storyapi.resource.request.story;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.codec.multipart.FilePart;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateStoryRequest {

    @NotEmpty
    @Length(max = 300)
    private String name;

    @Length(max = 4000)
    private String description;

    @NotNull
    private Integer complete;

    @Length(max = 250)
    private String author;

    @NotNull
    private Integer status;

    private FilePart thumbnail;

    private List<String> tags;

}
