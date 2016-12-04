/**
 * Created by Zebrata on 03.12.2016.
 */
import StorageService from "./StorageService";

module.exports= {

    getCurrentUserDevision() {
        let userToken = StorageService.getCurrentUser().user;
        let userDate = fetch('http://localhost:8008/api/auth/login', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmcm9udCIsImNyZWF0ZWQiOjE0ODA4NDQ1MjA4ODYsImV4cCI6MjM0NDg0NDUyMH0.r5CusvP3lHV8RsV3szjXwqhS2rD1Bb9YC4r1E0G8IMSbKyGUO4UiOSF_UPwmg8y3ZiOUpuamuz7v0mDiBFnqmw'
            })
        }).then(parseJson)
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

}
function parseJson(response) {
    return response.json();
}
