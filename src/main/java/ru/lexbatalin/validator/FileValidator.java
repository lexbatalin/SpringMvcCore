package ru.lexbatalin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UploadedFile file = (UploadedFile) o;

        if (file.getFile().getSize() == 0) {
            errors.rejectValue("file", "uploadForm.selectFile", "Please select a file!");
        }
    }
}
