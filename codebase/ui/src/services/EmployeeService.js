/**
 * Created by Zebrata on 03.12.2016.
 */
import StorageService from "./StorageService";
import Util from "./Util";

module.exports = {
    /**
     * @returns {*} the currents user division
     */
    getCurrentUserDivision() {
        return StorageService.getCurrentUserDivision();
    },

    /**
     * @returns {*|Promise.<TResult>} all available users as a promise
     */
    getEmployeesForCurrentDivision() {
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/users/division', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
        }).then(Util.checkStatus).then(Util.parseJson)
    },

};

