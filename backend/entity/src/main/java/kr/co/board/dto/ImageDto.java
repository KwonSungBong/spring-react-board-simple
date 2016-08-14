package kr.co.board.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ksb on 2016-08-07.
 */
public class ImageDto {
    @Data
    public static class Create {

    }

    @Component
    @Data
    public static class Response {
        private static String IMAGE_HOST_URL;
        private long imageIdx;
        private String imageName;

        private String thumbUrl;
        private String originUrl;

        public String getThumbUrl() {
            return IMAGE_HOST_URL + "/thumb/" + this.imageIdx + "-" + this.imageName;
        }

        public String getOriginUrl() {
            return IMAGE_HOST_URL + "/" + this.imageIdx + "-" + this.imageName;
        }

        @Value("${board.image-host-url}")
        public void setImageHostUrl(String imageHostUrl){
            IMAGE_HOST_URL = imageHostUrl;
        }
    }

    @Data
    public static class Update {
        private long imageIdx;
        private boolean enabled;
        private int sortOrder;
    }
}
