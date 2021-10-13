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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.lexbatalin.validator.FileValidator;
import ru.lexbatalin.validator.UploadedFile;

import java.io.*;

@Controller
@SessionAttributes("filename")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileValidator fileValidator;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView uploadFile(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) {

        ModelAndView modelAndView = new ModelAndView();

        String fileName = null;
        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        if (result.hasErrors()) {
            modelAndView.setViewName("main");
        } else {
            if (!file.isEmpty()) {
                try {
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
                } catch (IOException ex) {
                    LOG.error(ex.getMessage());
                }
            } else {
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/fileUploaded", method = RequestMethod.GET)
    public String fileUploaded() {
        return "fileUploaded";
    }
}
