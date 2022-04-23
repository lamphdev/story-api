package lamph.web.storyapi.resource.response;

import lombok.Data;

@Data
public class ImgBbUploadResponse {

    private ImgBbResponseData data;

    private Boolean success;
    private Integer status;

}
