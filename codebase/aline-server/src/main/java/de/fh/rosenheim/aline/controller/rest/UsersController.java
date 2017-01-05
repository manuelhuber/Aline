package de.fh.rosenheim.aline.controller.rest;

import de.fh.rosenheim.aline.model.dtos.generic.ErrorResponse;
import de.fh.rosenheim.aline.model.dtos.user.UserDTO;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import de.fh.rosenheim.aline.model.exceptions.NoObjectForIdException;
import de.fh.rosenheim.aline.service.UserService;
import de.fh.rosenheim.aline.util.ControllerUtil;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * All HTTP endpoints related to user
 */
@RestController
@RequestMapping("${route.user.base}")
public class UsersController {

    private final UserService userService;
    private final ControllerUtil controllerUtil;
    private final UserFactory userFactory;

    public UsersController(UserService userService, ControllerUtil controllerUtil, UserFactory userFactory) {
        this.userService = userService;
        this.controllerUtil = controllerUtil;
        this.userFactory = userFactory;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PostAuthorize("@securityService.canAccessUserData(principal, returnObject)")
    @ApiOperation(value = "get user info", notes = SwaggerTexts.GET_USER_DATA)
    public UserDTO user(
            @ApiParam(value = SwaggerTexts.SENSITIVE_DATA) @RequestParam(required = false, name = "name") String queryName,
            HttpServletRequest request) throws NoObjectForIdException {
        String name = queryName != null ? queryName : controllerUtil.getUsername(request);
        return userFactory.toUserDTO(userService.getUserByName(name));
    }

    @RequestMapping(value = "${route.user.all}", method = RequestMethod.GET)
    @PreAuthorize("@securityService.isFrontOffice(principal)")
    public List<String> getAllUserNames() {
        return userService.getAllUserNames();
    }

    @RequestMapping(value = "${route.user.division}", method = RequestMethod.GET)
    @PreAuthorize("@securityService.canGetDivisionUsers(principal, #queryDivision)")
    @ApiOperation(value = "get all users for division", notes = SwaggerTexts.GET_DIVISION_USERS)
    public List<UserDTO> getAllUsersForDivision(
            @ApiParam(value = SwaggerTexts.SENSITIVE_DATA) @RequestParam(name = "division", required = false) String queryDivision,
            HttpServletRequest request) throws NoObjectForIdException {

        String division = queryDivision;

        if (division == null || division.length() < 1) {
            division = userService.getUserByName(controllerUtil.getUsername(request)).getDivision();
        }

        return StreamSupport
                .stream(userService.getUsersForDivision(division).spliterator(), false)
                .map(userFactory::toUserDTO)
                .collect(Collectors.toList());
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
