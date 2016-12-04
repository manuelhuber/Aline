/**
 * Created by Zebrata on 03.12.2016.
 */
import StorageService from "./StorageService";

module.exports= {

    getCurrentUserDevision() {
        let userToken = StorageService.getCurrentUser().user;
        let userData = fetch('http://localhost:8008/api/auth/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: userToken
            })
        }).then(parseJson);
        return userData.devision
    },

    /**
     * @returns {*|Promise.<TResult>} all available users as a promise
     */
    getAllEmployee() {
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/users/all', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(parseJson)
    },

};
function parseJson(response) {
    return response.json();
}
