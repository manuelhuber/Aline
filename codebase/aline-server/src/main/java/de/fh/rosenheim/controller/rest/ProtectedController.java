package de.fh.rosenheim.controller.rest;

import de.fh.rosenheim.domain.entity.User;
import de.fh.rosenheim.repository.UserRepository;
import de.fh.rosenheim.security.impl.SecurityServiceImpl;
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

    /**
     * This is an example of some different kinds of granular restriction for endpoints. You can use the built-in SPEL
     * expressions in @PreAuthorize such as 'hasRole()' to determine if a user has access. However, if you require
     * logic beyond the methods Spring provides then you can encapsulate it in a service and register it as a bean to
     * use it within the annotation as demonstrated below with 'securityService'.
     **/
    @RequestMapping(method = RequestMethod.GET)
//    @PreAuthorize("hasAuthority('DIVISION_HEAD')")
    @PreAuthorize("@securityService.hasProtectedAccess()")
    public ResponseEntity<?> protectedStuff() {
        return ResponseEntity.ok(":O");
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @PostAuthorize("returnObject==null?false:returnObject.id == principal.id")
//    @PreAuthorize("@securityService.hasProtectedAccess()")
    public User user(@PathVariable int id) {
        return userRepository.findOne((long) id);
    }
}
