package lamph.web.storyapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "story_map_tag", uniqueConstraints = {
        @UniqueConstraint(name = "story_map_tag_uk1", columnNames = {"story_id", "tag"})
})
@NoArgsConstructor
public class StoryMapTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STR_MAP_TAG_SEQ")
    @SequenceGenerator(name = "STR_MAP_TAG_SEQ", sequenceName = "STR_MAP_TAG_SEQ",
            initialValue = 11111, allocationSize = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag", nullable = false)
    private Tag tag;

    public StoryMapTag(Story story, Tag tag) {
        this.story = story;
        this.tag = tag;
    }

}
