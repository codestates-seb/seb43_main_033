package main.main.company.controller;

import lombok.RequiredArgsConstructor;
import main.main.auth.interceptor.JwtParseInterceptor;
import main.main.company.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/companies")
@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    @PostMapping(path = "/{type}/{company-id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postImageUpload(@PathVariable("type") String type,
                                          @PathVariable("company-id") Long companyId,
                                          @RequestPart(required = false) MultipartFile file) {

        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        imageService.postImage(type, file, companyId, authenticationMemberId);

        String uri = imageService.getImage(type, companyId);

        Map<String, Object> response = new HashMap<>();
        response.put("companyId", companyId);
        response.put("uri", uri);

        return ResponseEntity.ok(response);

    }

    @GetMapping(path = "/{type}/{company-id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity getImageUri(@PathVariable("type") String type,
                                      @PathVariable("company-id") Long companyId) {

        String uri = imageService.getImage(type, companyId);

        if (uri != null) {
            return ResponseEntity.ok(uri);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PatchMapping(path = "/{type}/{company-id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchImageUpload( @PathVariable("type") String type,
                                            @PathVariable("company-id") Long companyId,
                                            @RequestPart(required = false) MultipartFile file) {

        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        imageService.patchImage(type, file, companyId, authenticationMemberId);

        String uri = imageService.getImage(type, companyId);

        Map<String, String> response = new HashMap<>();
        response.put("companyId", String.valueOf(companyId));
        response.put("uri", uri);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping(path = "/{type}/{company-id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity deleteImageUpload( @PathVariable("type") String type,
                                             @PathVariable("company-id") Long companyId) {

        long authenticationMemberId = JwtParseInterceptor.getAutheticatedMemberId();

        imageService.deleteImage(type, companyId, authenticationMemberId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
