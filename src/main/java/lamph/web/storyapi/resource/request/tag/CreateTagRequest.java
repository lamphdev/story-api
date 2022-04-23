package lamph.web.storyapi.resource.request.tag;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateTagRequest {

    @NotEmpty
    @Length(max = 100)
    private String name;

    @Length(max = 1000)
    private String description;

}
