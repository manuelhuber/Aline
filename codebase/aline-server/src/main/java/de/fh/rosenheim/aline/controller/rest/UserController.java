package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${route.user.base}")
public class UserController {

    private final UserService userService;
    private final ControllerUtil controllerUtil;

    public UserController(UserService userService, ControllerUtil controllerUtil) {
        this.userService = userService;
        this.controllerUtil = controllerUtil;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PostAuthorize("@securityService.canAccessUserData(principal, returnObject)")
    public User user(@RequestParam(required = false, name = "name") String queryName, HttpServletRequest request)
            throws NoObjectForIdException {
        String name = queryName != null ? queryName : controllerUtil.getUsername(request);
        return userService.getUserByName(name);
    }
}
