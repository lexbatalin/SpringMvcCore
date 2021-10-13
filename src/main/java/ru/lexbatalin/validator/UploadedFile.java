package ru.lexbatalin.validator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadedFile {

    private MultipartFile file;
}
