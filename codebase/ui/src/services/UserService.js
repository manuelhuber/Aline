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
            }
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * @returns {Promise.<TResult>|*} the user data of the given user name as a promise
     */
    getUser(userName) {
        let path = Util.getBasicUsersPath();
        if (userName) {
            path += '?name=' + userName;
        }
        let token = StorageService.getUserToken();
        return fetch(path, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
        }).then(Util.checkStatus).then(Util.parseJson)
    }
};
