package lamph.web.storyapi.resource.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImgBbResponseData {

    private String id;
    private String title;
    @JsonProperty("url_viewer")
    private String urlViewer;
    private String url;
    @JsonProperty("display_url")
    private String displayUrl;
    private Integer size;
    private Long time;
    private String expiration;

    private ImgBbFileInfo image;
    private ImgBbFileInfo thumb;
    private ImgBbFileInfo medium;

    @JsonProperty("delete_url")
    private String deleteUrl;

}
