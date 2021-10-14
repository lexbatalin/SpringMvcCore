package ru.lexbatalin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.lexbatalin.exception.BadFileNameException;
import ru.lexbatalin.validator.FileValidator;
import ru.lexbatalin.validator.UploadedFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@SessionAttributes("filename")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileValidator fileValidator;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView uploadFile(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) throws IOException, BadFileNameException {

        ModelAndView modelAndView = new ModelAndView();

        String fileName = null;
        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        if (result.hasErrors()) {
            modelAndView.setViewName("main");
        } else {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();

                fileName = file.getOriginalFilename();
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File uploadFile = new File(dir.getAbsoluteFile() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadFile));
                stream.write(bytes);
                stream.flush();
                stream.close();
                LOG.info("uploaded: " + uploadFile.getAbsolutePath());

                RedirectView redirectView = new RedirectView("fileUploaded");
                redirectView.setStatusCode(HttpStatus.FOUND);
                modelAndView.setView(redirectView);
                modelAndView.addObject("filename", fileName);
                throw new IOException("Folder not found");
//                throw new BadFileNameException("Bad filename: " + fileName);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/fileUploaded", method = RequestMethod.GET)
    public String fileUploaded() {
        return "fileUploaded";
    }

//    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "IOException! Check arguments!")
//    @ExceptionHandler(IOException.class)
//    public void handleIOException(){
//        LOG.error("IOException handler executed");
//    }

    @ExceptionHandler(BadFileNameException.class)
    public ModelAndView handleBadFileNameException(Exception ex) {
        LOG.error("BadFileNameException handler executed");
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}
