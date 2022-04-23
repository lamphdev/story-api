package lamph.web.storyapi.resource.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResponse<T> {

    private int page;
    private int limit;
    private long totals;
    private int pages;
    private List<T> content;

    public PageResponse(Page raw) {
        page = raw.getNumber();
        limit = raw.getSize();
        totals = raw.getTotalElements();
        pages = raw.getTotalPages();
        content = raw.getContent();
    }

}
