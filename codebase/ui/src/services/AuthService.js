/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";
import Util from './Util';

const AUTHORITIES = {
    EMPLOYEE: 'EMPLOYEE',
    DIVISION_HEAD: 'DIVISION_HEAD',
    FRONT_OFFICE: 'FRONT_OFFICE',
    TOP_DOG: 'TOP_DOG'
};

module.exports = {

    /**
     * @param userName the given name of the user
     * @param userPwd the given password of the user
     * @returns {Promise.<TResult>} the result as a promise
     */
    loginUser(userName, userPwd) {
        return fetch(Util.getBasicPath() + '/auth/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: userName,
                password: userPwd,
            })
        }).then(Util.checkStatus).then(Util.parseJson).then(result => {
            if (result.token && result.user) {
                StorageService.storeUserToken(result.token);
                StorageService.storeCurrentUser(result.user);
            }
        })
    },

    /**
     * Logout the current user
     */
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
        let userRoles = StorageService.getUserAuthorities();
        return userRoles.includes(AUTHORITIES.DIVISION_HEAD);
    },

    /**
     * @returns true if the user has the authority front office
     */
    isFrontOffice(){
        let userRoles = StorageService.getUserAuthorities();
        return userRoles.includes(AUTHORITIES.FRONT_OFFICE);
    },

    /**
     * @returns true if the user has the authority employee
     */
    isEmployee(){
        let userRoles = StorageService.getUserAuthorities();
        return userRoles.includes(AUTHORITIES.EMPLOYEE);
    },

    /**
     * @returns true if the user has the authority top-dog
     */
    isTopDog(){
        let userRoles = StorageService.getUserAuthorities();
        return userRoles.includes(AUTHORITIES.TOP_DOG);
    }
};
