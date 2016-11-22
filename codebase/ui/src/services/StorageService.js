/**
 * The StorageService, managing the session storage for
 * saving information like username, - roles and token
 *
 * Created by franziskah on 02.11.16.
 */
const USER_NAME_KEY = 'userName';
const USER_TOKEN_KEY = 'userToken';
const USER_ROLES_KEY = 'userRoles';

module.exports = {

    /**
     * @param userName the user of the current session
     * @param userRoles the users roles
     */
    storeCurrentUser(userName, userRoles) {
        sessionStorage.setItem(USER_NAME_KEY, userName);
        sessionStorage.setItem(USER_ROLES_KEY, userRoles);
    },

    /**
     * @returns {{user, roles}} the users name as a string and his roles as an array[]
     */
    getCurrentUser() {
        return {
            'user': sessionStorage.getItem(USER_NAME_KEY),
            'roles': sessionStorage.getItem(USER_ROLES_KEY)
        }
    },

    /**
     * Store the users personal token into the session storage
     * @param userToken the token to store
     */
    storeUserToken(userToken) {
        sessionStorage.setItem(USER_TOKEN_KEY, userToken);
    },

    /**
     * @returns the users personal token out of the session storage
     */
    getUserToken() {
        return sessionStorage.getItem(USER_TOKEN_KEY);
    },

    /**
     * Delete the local stored user information
     */
    deleteLocalUserInformation(){
        sessionStorage.removeItem(USER_NAME_KEY);
        sessionStorage.removeItem(USER_ROLES_KEY);
        sessionStorage.removeItem(USER_TOKEN_KEY);
    }
};

