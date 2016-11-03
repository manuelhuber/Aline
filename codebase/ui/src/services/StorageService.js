/**
 * The StorageService, managing the session storage for
 * saving information like username, - roles and token
 *
 * Created by franziskah on 02.11.16.
 */

const USER_NAME_KEY = 'userName';
const USER_TOKEN_KEY = 'userToken';
const USER_ROLES_KEY = 'userRoles';

/**
 * @param userName the user of the current session
 */
export function storeCurrentUser(userName, userRoles) {
    sessionStorage.setItem(USER_NAME_KEY, userName);
    sessionStorage.setItem(USER_ROLES_KEY, userRoles);
    console.log('userName: ' + userName + ' and userRoles:' + userRoles + ' got stored.');
}

/**
 * @returns {{user, roles}} the users name as a string and his roles as an array[]
 */
export function getCurrentUser() {
    return {
        'user': sessionStorage.getItem(USER_NAME_KEY),
        'roles': sessionStorage.getItem(USER_ROLES_KEY)
    }
}

/**
 * Store the users personal token into the session storage
 * @param userToken the token to store
 */
export function storeUserToken(userToken) {
    sessionStorage.setItem(USER_TOKEN_KEY, userToken);
    console.log('userToken: ' + userToken + ' got stored.');

}

/**
 * @returns the users personal token out of the session storage
 */
export function getUserToken() {
    return sessionStorage.getItem(USER_TOKEN_KEY);
}
