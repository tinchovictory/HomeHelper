package ar.edu.itba.paw.homehelper.controller;

import ar.edu.itba.paw.homehelper.auth.HHUserDetailsService;
import ar.edu.itba.paw.homehelper.exceptions.ProviderNotFoundException;
import ar.edu.itba.paw.homehelper.form.AppointmentForm;
import ar.edu.itba.paw.homehelper.form.SearchForm;
import ar.edu.itba.paw.homehelper.form.SignUpForm;
import ar.edu.itba.paw.homehelper.validators.EqualsUsernameValidator;
import ar.edu.itba.paw.interfaces.services.MailService;
import ar.edu.itba.paw.interfaces.services.NeighborhoodService;
import ar.edu.itba.paw.interfaces.services.SProviderService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class PublicController {
    @Autowired
    private SProviderService sProviderService;

    @Autowired
    private NeighborhoodService neighborhoodService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EqualsUsernameValidator equalsUsernameValidator;

    @Autowired
    private HHUserDetailsService userDetailsService;

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }

    @ModelAttribute("appointmentForm")
    public AppointmentForm appointmentForm() {
        return new AppointmentForm();
    }

    @ModelAttribute("signUpForm")
    public SignUpForm signUpForm() {
        return new SignUpForm();
    }


    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("loggedInUser") final User loggedInUser) {
        final ModelAndView mav = new ModelAndView("index");

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("serviceTypes", sProviderService.getServiceTypes());
        mav.addObject("neighborhoods", neighborhoodService.getAllNeighborhoods());
        return mav;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView processSearch(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("searchForm") final SearchForm form, final BindingResult errors, final RedirectAttributes redrAttr, @RequestHeader(value = "referer", required = false, defaultValue = "/") final String referer, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, @RequestParam(required = false, value = "cty", defaultValue = "-1") final int cityId) {

        if(errors.hasErrors()) {
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.searchForm", errors);
            redrAttr.addFlashAttribute("searchForm", form);
            String redirect = "redirect:";

            if(referer.contains("/searchResults")) {
                /* Referer page is searchResults, send it back there */
                redirect += "/searchResults?st=" + serviceTypeId + "&cty=" + cityId;
            } else {
                /* Referer page is index */
                redirect += "/";
            }

            return new ModelAndView(redirect);
        }

        redrAttr.addFlashAttribute("searchForm", form);
        String redirect = "redirect:/searchResults?st=" + form.getServiceTypeId() + "&cty=" + form.getCityId();
        return new ModelAndView(redirect);
    }

    @RequestMapping(value = "/searchResults", method = RequestMethod.GET)
    public ModelAndView searchProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @RequestParam(required = false, value = "st", defaultValue = "-1") final int serviceTypeId, @RequestParam(required = false, value = "cty", defaultValue = "-1") final int cityId) {
        final ModelAndView mav = new ModelAndView("profileSearch");

        /* Lanzar excepcion cuando serviceTypeId es -1 o cuando cityId es -1 */

        final List<SProvider> list = sProviderService.getServiceProvidersWithServiceType(serviceTypeId);;


        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));

        mav.addObject("list",list);
        mav.addObject("serviceTypes",sProviderService.getServiceTypes());

        /* Current params showing */
        mav.addObject("serviceTypeId", serviceTypeId);
        mav.addObject("cityId", cityId);

        mav.addObject("neighborhoods", neighborhoodService.getAllNeighborhoods());




        return mav;
    }

    @RequestMapping("/profile/{providerId}")
    public ModelAndView providerProfile(@ModelAttribute("loggedInUser") final User loggedInUser, @PathVariable("providerId") int providerId) {
        final ModelAndView mav = new ModelAndView("profile");

        final SProvider provider = sProviderService.getServiceProviderWithUserId(providerId);

        if(provider == null) {
            throw new ProviderNotFoundException();
        }

        mav.addObject("user", loggedInUser);
        mav.addObject("userProviderId", sProviderService.getServiceProviderId(getUserId(loggedInUser)));
        mav.addObject("provider",provider);



        return mav;
    }


    @ResponseBody
    @RequestMapping(value = "/profile/{userId}/profileimage", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] providerProfileImage(@PathVariable("userId") int userId) {
        return userService.getProfileImage(userId);
    }


    @RequestMapping(value = "/profile/sendAppointment", method = RequestMethod.POST)
    public ModelAndView sendAppointment(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("appointmentForm") final AppointmentForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        if(errors.hasErrors()) {
            /* Back to form */
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.appointmentForm", errors);
            redrAttr.addFlashAttribute("appointmentForm", form);

            String redirect = "redirect:/profile/" + form.getProviderId();
            return new ModelAndView(redirect);
        }

        redrAttr.addFlashAttribute("appointmentForm", form);

        if(loggedInUser == null) {
            String redirect = "redirect:/login";
            return new ModelAndView(redirect);
        }

        return new ModelAndView("forward:/client/sendAppointment");
    }

    @RequestMapping("/signup")
    public ModelAndView signup(@ModelAttribute("loggedInUser") final User loggedInUser/*, @ModelAttribute("signUpForm") final SignUpForm signUpForm*/) {
        final ModelAndView mav = new ModelAndView("signup");

        mav.addObject("user", loggedInUser);

        /*if (signUpForm != null && signUpForm.getProfilePicture() != null) {
            try {
                mav.addObject("profilePicture", signUpForm.getProfilePicture().getBytes());
            } catch (IOException e) {
                mav.addObject("profilePicture", null);
            }
        } else {
            mav.addObject("profilePicture", null);
        }*/

        return mav;
    }


    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView createUser(@ModelAttribute("loggedInUser") final User loggedInUser, @Valid @ModelAttribute("signUpForm") final SignUpForm form, final BindingResult errors, final RedirectAttributes redrAttr) {
        byte[] image;
        User invalidUser =  userService.findByUsername(form.getUsername());

        /* Check for duplicate username */
        if(invalidUser != null) {
            equalsUsernameValidator.validate(EqualsUsernameValidator.buildUserNamePair(form.getUsername(),invalidUser.getUsername()), errors);
        }

        if (errors.hasErrors()) {
            /* Back to form */
            redrAttr.addFlashAttribute("org.springframework.validation.BindingResult.signUpForm", errors);
            redrAttr.addFlashAttribute("signUpForm", form);

            return new ModelAndView("redirect:/signup");
        }

        try{
            image =form.getProfilePicture().getBytes();
        }catch (Exception e){
            e.printStackTrace();
            image = null;
        }

        User user = userService.create(form.getUsername(), form.getPasswordForm().getPassword(), form.getFirstname(), form.getLastname(), form.getEmail(), form.getPhone(), form.getEmail(), image);
        mailService.sendConfirmationEmail(user.getEmail(), user.getId());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/");
    }

    private int getUserId(User user) {
        if(user == null) {
            return -1;
        }
        return user.getId();
    }
}
