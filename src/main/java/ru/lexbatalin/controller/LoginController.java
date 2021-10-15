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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import ru.lexbatalin.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

import static java.util.Objects.nonNull;

@Controller
@SessionAttributes("user")
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private static final int ZERO_SHORT = 0;
    private static final int WEAK_STRENGTH = 3;
    private static final int FEAR_STRENGTH = 5;
    private static final int STRONG_STRENGTH = 7;

    private static final String TOO_SHORT_COLOR = "#FF11FF";
    private static final String WEAK_COLOR = "#FF0000";
    private static final String FEAR_COLOR = "#FF9900";
    private static final String STRONG_COLOR = "#0099CC";

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute
    public User createNewUser() {
        return new User("userNameValue");
    }

    @RequestMapping(value = "/checkStrength", method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE + "; charset=UTF-8")
    public @ResponseBody String checkStrength(@RequestParam String password) {

        String style = "<span style=\"color:%s; font-weight:bold;\">%s</span>";

        int len = password.length();
        if (len > ZERO_SHORT && len < WEAK_STRENGTH) {
            return String.format(style, TOO_SHORT_COLOR, "Too short password");
        } else if(len >= WEAK_STRENGTH && len < FEAR_STRENGTH) {
            return String.format(style, WEAK_COLOR, "Too short password");
        } else if (len >= FEAR_STRENGTH && len < STRONG_STRENGTH) {
            return String.format(style, FEAR_COLOR, "Medium password");
        } else if (len >= STRONG_STRENGTH) {
            return String.format(style, STRONG_COLOR, "Strong password");
        }
        return "";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView login(@ModelAttribute User user, Locale locale) {
        System.out.println(locale.getDisplayLanguage());
        System.out.println(messageSource.getMessage("locale",
                new String[] {locale.getDisplayName(locale)}, locale));
        return new ModelAndView("login", "user", user);
    }

    @RequestMapping(value = "/check_user", method = RequestMethod.POST)
    public ModelAndView checkUser(Locale locale, @Valid  @ModelAttribute("user") User user, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login");
        } else {
            RedirectView redirectView = new RedirectView("mainPage");
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            modelAndView.setView(redirectView);
            redirectAttributes.addFlashAttribute("locale", messageSource.getMessage("locale",
                    new String[] {locale.getDisplayName(locale)}, locale));
        }
        return modelAndView;
    }

    @RequestMapping(value = "/mainPage", method = RequestMethod.GET)
    public String goMainPage(HttpServletRequest request) {
        Map<String, ? > map = RequestContextUtils.getInputFlashMap(request);
        if (nonNull(map)) {
            LOG.info("redirect!");
        } else {
            LOG.info("update!");
        }
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
