package kh.com.kshrd.movieapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import kh.com.kshrd.movieapi.dto.request.CastMemberRequest;
import kh.com.kshrd.movieapi.dto.response.APIResponse;
import kh.com.kshrd.movieapi.model.CastMember;
import kh.com.kshrd.movieapi.service.CastMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cast-members")
@RequiredArgsConstructor
@Tag(name = "Cast Members", description = "Manage cast members related to movies")
@SecurityRequirement(name = "bearerAuth")
public class CastMemberController {

    private final CastMemberService castService;

    @PostMapping
    @Operation(summary = "Create a cast member")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<CastMember>> create(@RequestBody @Valid CastMemberRequest request) {
        CastMember created = castService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                APIResponse.<CastMember>builder()
                        .message("Cast member created successfully")
                        .payload(created)
                        .status(HttpStatus.CREATED)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping
    @Operation(summary = "Get all cast members with pagination")
    public ResponseEntity<APIResponse<List<CastMember>>> getAll(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ) {
        List<CastMember> castList = castService.getAll(page, size);
        return ResponseEntity.ok(
                APIResponse.<List<CastMember>>builder()
                        .message("Cast members retrieved successfully")
                        .payload(castList)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping("/{cast-member-id}")
    @Operation(summary = "Get cast member by ID")
    public ResponseEntity<APIResponse<CastMember>> getById(@PathVariable("cast-member-id") Long castMemberId) {
        CastMember cast = castService.getById(castMemberId);
        return ResponseEntity.ok(
                APIResponse.<CastMember>builder()
                        .message("Cast member retrieved successfully")
                        .payload(cast)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @GetMapping("/movies/{movie-id}")
    @Operation(summary = "Get all cast members by movie ID")
    public ResponseEntity<APIResponse<List<CastMember>>> getByMovieId(@PathVariable("movie-id") Long movieId) {
        List<CastMember> castList = castService.getByMovieId(movieId);
        return ResponseEntity.ok(
                APIResponse.<List<CastMember>>builder()
                        .message("Cast members retrieved successfully by movie")
                        .payload(castList)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @PutMapping("/{cast-member-id}")
    @Operation(summary = "Update a cast member by ID")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<CastMember>> update(@PathVariable("cast-member-id") Long castMemberId,
                                                          @RequestBody @Valid CastMemberRequest request) {
        CastMember updated = castService.update(castMemberId, request);
        return ResponseEntity.ok(
                APIResponse.<CastMember>builder()
                        .message("Cast member updated successfully")
                        .payload(updated)
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    @DeleteMapping("/{cast-member-id}")
    @Operation(summary = "Delete a cast member by ID")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<APIResponse<Void>> delete(@PathVariable("cast-member-id") Long castMemberId) {
        castService.delete(castMemberId);
        return ResponseEntity.ok(
                APIResponse.<Void>builder()
                        .message("Cast member deleted successfully")
                        .status(HttpStatus.OK)
                        .timestamp(Instant.now())
                        .build()
        );
    }
}
