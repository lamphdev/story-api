package lamph.web.storyapi.resource.request;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class PageRequest {

    private int page;
    private int limit;

    public Pageable getPageable() {
        if (page < 0 )
            page = 0;
        if (limit <= 0)
            limit = 50;

        return org.springframework.data.domain.PageRequest.of(page, limit);
    }
}
