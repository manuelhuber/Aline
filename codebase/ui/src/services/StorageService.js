/**
 * The StorageService, managing the session storage for
 * saving information like username, - roles and token
 *
 * Created by franziskah on 02.11.16.
 */
const USER_NAME_KEY = 'userName';
const USER_TOKEN_KEY = 'userToken';
const USER_ROLES_KEY = 'userRoles';
const USER_FIRSTNAME_KEY = 'userFirstName';
const USER_LASTNAME_KEY = 'userLastName';
const USER_DIVISION_KEY = 'userDivision';
const USER_BOOKINGS_KEY = 'userBookings';

module.exports = {

    /**
     * @param user the user object of the current session
     */
    storeCurrentUser(user) {
        sessionStorage.setItem(USER_NAME_KEY, user.username);
        sessionStorage.setItem(USER_ROLES_KEY, user.authorities);
        sessionStorage.setItem(USER_FIRSTNAME_KEY, user.firstName);
        sessionStorage.setItem(USER_LASTNAME_KEY, user.lastName);
        sessionStorage.setItem(USER_DIVISION_KEY, user.division);
        sessionStorage.setItem(USER_BOOKINGS_KEY, user.bookings);
    },

    /**
     * Returns the current user as an user object
     */
    getCurrentUser() {
        return {
            'userName': sessionStorage.getItem(USER_NAME_KEY),
            'roles': sessionStorage.getItem(USER_ROLES_KEY),
            'firstName': sessionStorage.getItem(USER_FIRSTNAME_KEY),
            'lastName': sessionStorage.getItem(USER_LASTNAME_KEY),
            'userDivision': sessionStorage.getItem(USER_DIVISION_KEY),
            'bookings': sessionStorage.getItem(USER_BOOKINGS_KEY)
        }
    },

    /**
     * Return the currents user division
     */
    getCurrentUserDivision(){
        return sessionStorage.getItem(USER_DIVISION_KEY);
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
     * @returns {string} the users full name
     */
    getUserFullName(){
        let firstName = sessionStorage.getItem(USER_FIRSTNAME_KEY);
        let lastName = sessionStorage.getItem(USER_LASTNAME_KEY);
        return firstName + ' ' + lastName;
    },

    /**
     * @returns {*} the authorities of the current logged in user
     */
    getUserAuthorities(){
        return sessionStorage.getItem(USER_ROLES_KEY);
    },

    /**
     * Delete the local stored user information
     */
    deleteLocalUserInformation(){
        sessionStorage.clear();
    }
};

