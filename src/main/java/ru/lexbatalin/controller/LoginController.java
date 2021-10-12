package ru.lexbatalin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.lexbatalin.model.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login(@ModelAttribute User user, Locale locale) {
        System.out.println(locale.getDisplayLanguage());
        System.out.println(messageSource.getMessage("locale",
                new String[] {locale.getDisplayName(locale)}, locale));
        user.setName("userNameValue");
        return new ModelAndView("login", "user", user);
    }

    @RequestMapping(value = "/check_user", method = RequestMethod.POST)
    public ModelAndView checkUser(Locale locale, @Valid  @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("locale", messageSource.getMessage("locale",
                new String[] {locale.getDisplayName(locale)}, locale));

        modelAndView.setViewName(bindingResult.hasErrors() ? "login" : "main");
        return modelAndView;
    }

    @RequestMapping(value = "/failed", method = RequestMethod.GET)
    public ModelAndView failed() {
        return new ModelAndView("login_failed", "message", "Login failed!");
    }

    @RequestMapping(value = "/get_json_user" , method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getJsonUser(@RequestParam("name") String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    @RequestMapping(value = "/get_json_user/{name}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody User getJsonUserByPath(@PathVariable("name") String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    @RequestMapping(value = "get_xml_user", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody User getXmlUser(@RequestParam("name") String name, @RequestParam("isAdmin") boolean admin) {
        User user = new User();
        user.setName(name);
        user.setIsAdmin(admin);
        return user;
    }

    @RequestMapping(value = "/put_json_user", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> setJsonUser(@RequestBody User user) {
        LOG.info(user.getName());
        return new ResponseEntity<String>(HttpStatus.ACCEPTED);
    }
}
