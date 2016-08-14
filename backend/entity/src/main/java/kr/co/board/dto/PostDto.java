package kr.co.board.dto;

import lombok.Data;

/**
 * Created by ksb on 2016-08-06.
 */
public class PostDto {

    @Data
    public static class Summary {
        public static int CONTENT_SUMMARY_START_INDEX = 0;
        public static int CONTENT_SUMMARY_END_INDEX = 200;

        private Long idx;
        private String subject;
        private String content;
        private ImageGroupDto.Response imageGroup;

        public String getSummaryContent(){
            if(content.length()<CONTENT_SUMMARY_END_INDEX) {
                return content;
            } else {
                return content.substring(CONTENT_SUMMARY_START_INDEX, CONTENT_SUMMARY_END_INDEX -3) + "...";
            }
        }

        public boolean isExcessSummaryContent() {
            if (content.length() < CONTENT_SUMMARY_END_INDEX) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Data
    public static class Detail {
        private Long idx;
        private String subject;
        private String content;
        private ImageGroupDto.Response imageGroup;
    }

    @Data
    public static class Create {
        private String subject;
        private String content;
    }

    @Data
    public static class Update {
        private Long idx;
        private String subject;
        private String content;
        private ImageGroupDto.Response imageGroup;
    }
}
