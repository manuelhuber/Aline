package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.controller.util.SwaggerTexts;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.model.json.response.ErrorResponse;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "get user info", notes = "Returns the data of the current user (detected via token)")
    public User user(
            @ApiParam(value = SwaggerTexts.OTHER_USER) @RequestParam(required = false, name = "name") String queryName,
            HttpServletRequest request) throws NoObjectForIdException {
        String name = queryName != null ? queryName : controllerUtil.getUsername(request);
        return userService.getUserByName(name);
    }

    /**
     * Custom response if no User for the given name exists
     */
    @ExceptionHandler(NoObjectForIdException.class)
    public ResponseEntity<ErrorResponse> noObjectException(NoObjectForIdException ex) {
        return new ResponseEntity<>(
                new ErrorResponse("No user with name=" + ex.getId()),
                HttpStatus.NOT_FOUND);
    }
}
