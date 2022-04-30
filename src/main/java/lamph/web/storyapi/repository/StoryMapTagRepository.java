package lamph.web.storyapi.repository;

import lamph.web.storyapi.domain.StoryMapTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryMapTagRepository extends JpaRepository<StoryMapTag, Long> {

    @Modifying
    void deleteAllByStoryId(Long storyId);

}
