/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";

module.exports = {

    /**
     * @returns {*|Promise.<TResult>} all available seminars as a promise
     */
    getAllSeminars() {
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(parseJson)
    },

    /**
     * @returns {*|Promise.<TResult>} all available categories as a promise
     */
    getAllCategories() {
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars/categories', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(parseJson)
    },

    /**
     * @param seminarId the id of the seminar to get
     * @returns {*|Promise.<TResult>} The seminar with the given ID as a promise
     */
    getSeminarById(seminarId){
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars/' + seminarId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
        }).then(parseJson)
    },


    addSeminar(seminar){
        fetch('http://localhost:8008/api/seminars', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': StorageService.getUserToken()
            },
            body: JSON.stringify(seminar)
        }).then(checkStatus)
    },

    getTargetLevels(){
        return [1, 2, 3, 4, 5]
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
 * @param response the response got by the server
 */
function checkStatus(response) {
    if (!response.ok) {
        console.log('Der Response des Servers beim Aufruf der addSeminar Methode war nicht in Ordnung:' + response);
    }
}