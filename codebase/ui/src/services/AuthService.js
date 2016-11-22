/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";

const AUTHORITIES = {
    EMPLOYEE: 'EMPLOYEE',
    DIVISION_HEAD: 'DIVISION_HEAD',
    FRONT_OFFICE: 'FRONT_OFFICE'
};

module.exports = {

    loginUser(userName, userPwd) {
        return fetch('http://localhost:8008/api/auth/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: userName,
                password: userPwd,
            })
        }).then(parseJson).then(result => {
            if (result.token && result.authorities) {
                StorageService.storeUserToken(result.token);
                StorageService.storeCurrentUser(userName, result.authorities);
            }
        })
    },

    logoutUser(){
        sessionStorage.clear();
    },

    /**
     * @returns true if the user is logged in
     */
    isLoggedIn(){
        return StorageService.getUserToken();
    },

    /**
     * @returns true if the user has the authority division head
     */
    isDivisionHead(){
        let userRoles = StorageService.getCurrentUser().roles;
        return userRoles.includes(AUTHORITIES.DIVISION_HEAD);
    },

    /**
     * @returns true if the user has the authority front office
     */
    isFrontOffice(){
        let userRoles = StorageService.getCurrentUser().roles;
        return userRoles.includes(AUTHORITIES.FRONT_OFFICE);
    }
};

/**
 * @param response the response to parse
 * @returns {*} the response, parsed to json
 */
function parseJson(response) {
    return response.json();
}

/**
 * Check the status of the response
 * @param response the response we got by the fetch function
 */
function checkStatus(response) {
    if (response.status >= 200 && response.status < 300) {
        return response;
    } else {
        var error = new Error(response.statusText);
        error.response = response;
        throw error;
    }
}