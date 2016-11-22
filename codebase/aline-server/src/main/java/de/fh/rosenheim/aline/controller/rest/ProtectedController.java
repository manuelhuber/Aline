package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.repository.BookingRepository;
import de.fh.rosenheim.aline.repository.UserRepository;
import de.fh.rosenheim.aline.security.service.SecurityServiceImpl;
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

    private final UserRepository userRepository;

    private final BookingRepository bookingRepository;

    public ProtectedController(UserRepository userRepository, BookingRepository bookingRepository, SecurityServiceImpl securityService) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

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

    @RequestMapping(value = "/user/{name}", method = RequestMethod.GET)
    @PostAuthorize("@securityService.canAccessUserData(principal, returnObject)")
    public User user(@PathVariable String name) {
        return userRepository.findByUsername(name);
    }

    @RequestMapping(value = "/booking/{id}", method = RequestMethod.GET)
    public Booking booking(@PathVariable int id) {
        return bookingRepository.findOne((long) id);
    }
}
