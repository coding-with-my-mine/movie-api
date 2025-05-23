package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.FileMetadata;
import kh.com.kshrd.movieapi.service.FileService;
import kh.com.kshrd.movieapi.util.MediaTypeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "Endpoints for uploading and previewing files")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "Upload a file", description = "Upload a file to the server")
    @PostMapping(value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<APIResponse<FileMetadata>> uploadFile(@RequestParam MultipartFile file) {
        FileMetadata fileMetadata = fileService.uploadFile(file);
        APIResponse<FileMetadata> apiResponse = APIResponse.<FileMetadata>builder()
                .message("File uploaded successfully")
                .status(HttpStatus.CREATED)
                .payload(fileMetadata)
                .timestamp(Instant.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "Preview a file", description = "Preview a file (e.g., image) by filename")
    @GetMapping("/preview-file/{file-name}")
    public ResponseEntity<?> getFileByFileName(@PathVariable("file-name") String fileName) throws IOException {
        InputStream inputStream = fileService.getFileByFileName(fileName);
        byte[] fileContent = inputStream.readAllBytes();
        MediaType mediaType = MediaTypeUtil.getMediaType(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(fileContent);
    }

}
