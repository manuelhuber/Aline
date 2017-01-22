/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";
import Util from './Util';

module.exports = {

    /**
     * @returns {*|Promise.<TResult>} all available seminars as a promise
     */
    getAllSeminars() {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath(), {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * @returns {*|Promise.<TResult>} all current available seminars as a promise
     */
    getCurrentSeminars() {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/current', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(Util.checkStatus).then(Util.parseJson)
    },


    /**
     * @returns {*|Promise.<TResult>} all past available seminars as a promise
     */
    getPastSeminars() {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/past', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * @returns {*|Promise.<TResult>} all available categories as a promise
     */
    getAllCategories() {
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/categories', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: {}
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * @param seminarId the id of the seminar to get
     * @returns {*|Promise.<TResult>} The seminar with the given ID as a promise
     */
    getSeminarById(seminarId){
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/' + seminarId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * Add the given seminar
     * @param seminar the seminar to add
     */
    addSeminar(seminar){
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath(), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify(seminar)
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * Update the given seminar
     * @param seminar the seminar to update
     * @param seminarId the id of the seminar to update
     */
    updateSeminar(seminar, seminarId){
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/' + seminarId, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify(seminar)
        }).then(Util.checkStatus).then(Util.parseJson)
    },
    /**
     * Delete the seminar with the given id
     * @param seminarId the id of the seminar to delete
     */
    deleteSeminar(seminarId){
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/' + seminarId, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
        }).then(Util.checkStatus).then(Util.checkStatus)
    },

    /**
     * @param seminarId the id of the seminar to get the bill for
     * @returns {*|Promise.<TResult>} The seminars bill with the given ID as a promise
     */
    generateBillForSeminar(seminarId){
        let token = StorageService.getUserToken();
        return fetch(Util.getBasicSeminarsPath() + '/' + seminarId + '/bill', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            }
        }).then(Util.checkStatus).then(Util.parseJson)
    },

    /**
     * @returns {number[]} the available targetLevels
     */
    getTargetLevels(){
        return [1, 2, 3, 4, 5]
    }
};

