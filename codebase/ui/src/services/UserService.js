/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";
import Util from './Util';

module.exports = {
    /**
     * @returns {*|Promise.<TResult>} all available usernames as a promise
     */
    getAllUsers() {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicUsersPath() + '/all', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(Util.parseJson)
    },

    /**
     *
     * @returns {Promise.<TResult>|*} the current logged in user as a promise
     */
    getUser() {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicUsersPath(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(Util.parseJson)
    },

    /**
     * @returns {Promise.<TResult>|*} the user data of the given user name as a promise
     */
    getUser(userName) {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicUsersPath(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {
                name: userName
            }
        }).then(Util.parseJson)
    },

};
