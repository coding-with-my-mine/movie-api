package kh.com.kshrd.movieapi.util;

import org.springframework.http.MediaType;

public class MediaTypeUtil {

    public static MediaType getMediaType(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();

        if (lowerCaseFileName.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (lowerCaseFileName.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (lowerCaseFileName.endsWith(".mp4")) {
            return MediaType.parseMediaType("video/mp4");
        } else if (lowerCaseFileName.endsWith(".webm")) {
            return MediaType.parseMediaType("video/webm");
        } else if (lowerCaseFileName.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (lowerCaseFileName.endsWith(".json")) {
            return MediaType.APPLICATION_JSON;
        }

        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
