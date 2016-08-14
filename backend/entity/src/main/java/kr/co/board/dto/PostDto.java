package kr.co.board.dto;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        public String getSummaryContent() {
            String removeTagContent = removeTag(this.content);
            if(removeTagContent.length()<CONTENT_SUMMARY_END_INDEX) {
                return removeTagContent;
            } else {
                return removeTagContent.substring(CONTENT_SUMMARY_START_INDEX, CONTENT_SUMMARY_END_INDEX -3) + "...";
            }
        }

        public String getSummaryImage(){
            Pattern pattern  =  Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
            Matcher match = pattern.matcher(content);
            String imgTag = null;
            String src = null;

            if(match.find()){
                imgTag = match.group(0);

                Pattern pattern2 = Pattern.compile("src=\"(.*?)\"");
                Matcher match2 = pattern2.matcher(imgTag);
                if (match2.find()) {
                    src = match2.group(1);
                }
            }
            return src;
        }

        public String removeTag(String html) {
            return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
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
