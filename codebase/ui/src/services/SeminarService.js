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
     * @returns {*|Promise.<TResult>} all current available seminars as a promise
     */
    getCurrentSeminars() {
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars/current', {
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
     * @returns {*|Promise.<TResult>} all past available seminars as a promise
     */
    getPastSeminars() {
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars/past', {
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

    /**
     * Add the given seminar
     * @param seminar the seminar to add
     */
    addSeminar(seminar){
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify(seminar)
        }).then(parseJson)
    },

    /**
     * Update the given seminar
     * @param seminar the seminar to update
     * @param seminarId the id of the seminar to update
     */
    updateSeminar(seminar, seminarId){
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars/' + seminarId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify(seminar)
        }).then(parseJson)
    },
    /**
     * Delete the seminar with the given id
     * @param seminarId the id of the seminar to delete
     */
    deleteSeminar(seminarId){
        let token = StorageService.getUserToken();
        return fetch('http://localhost:8008/api/seminars/' + seminarId, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
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