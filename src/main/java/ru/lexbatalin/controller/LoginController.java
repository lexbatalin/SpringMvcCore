package ru.lexbatalin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.lexbatalin.model.User;

import javax.validation.Valid;

@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("login", "user", new User());
    }

    @RequestMapping(value = "/check_user", method = RequestMethod.POST)
    public String checkUser(@Valid  @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        model.addAttribute("user", user);
        return "main";
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
