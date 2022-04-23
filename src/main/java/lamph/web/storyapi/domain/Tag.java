package lamph.web.storyapi.domain;

import lamph.web.storyapi.resource.request.tag.CreateTagRequest;
import lamph.web.storyapi.resource.request.tag.UpdateTagRequest;
import lamph.web.storyapi.utils.IdUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tag")
public class Tag {

    @Id
    private String id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Integer status;

    public Tag(CreateTagRequest request) {
        this.id = IdUtils.fromString(request.getName());
        this.name = request.getName();
        this.description = request.getDescription();
        this.status = 1;
    }

    public Tag updateData(UpdateTagRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.status = request.getStatus();
        return this;
    }

}
