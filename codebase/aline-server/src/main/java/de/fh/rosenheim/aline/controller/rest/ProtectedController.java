package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.domain.entity.Booking;
import de.fh.rosenheim.aline.domain.entity.User;
import de.fh.rosenheim.aline.repository.BookingRepository;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.security.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("protected")
public class ProtectedController {

    @Autowired
    SecurityServiceImpl securityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    /**
     * This is an example of some different kinds of granular restriction for endpoints. You can use the built-in SPEL
     * expressions in @PreAuthorize such as 'hasRole()' to determine if a user has access. However, if you require
     * logic beyond the methods Spring provides then you can encapsulate it in a service and register it as a bean to
     * use it within the annotation as demonstrated below with 'securityService'.
     **/
    @RequestMapping(method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('DIVISION_HEAD')")
    @PreAuthorize("@securityService.isDivisionHead()")
    public ResponseEntity<?> protectedStuff() {
        return ResponseEntity.ok(":O");
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @PreAuthorize("@securityService.divisionHeadOrSelf(principal, #id)")
    @PostAuthorize("returnObject.division == principal.division")
    public User user(@PathVariable int id) {
        return userRepository.findOne((long) id);
    }

    @RequestMapping(value = "/booking/{id}", method = RequestMethod.GET)
    public Booking booking(@PathVariable int id) {
        return bookingRepository.findOne((long) id);
    }
}
