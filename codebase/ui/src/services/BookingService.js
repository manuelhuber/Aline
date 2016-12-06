/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";

module.exports = {
    /**
     * Book the seminar with the given id
     * @param id
     */
    bookSeminar(id){
        let token = StorageService.getUserToken();
        let userName = StorageService.getCurrentUser().userName;
        return fetch('http://localhost:8008/api/bookings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify({
                seminarId: id,
                userName: userName
            })
        }).then(checkStatus).then(parseJson)
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
    if (response.status >= 200 && response.status < 300) {
        return response;
    } else {
        var error = new Error(response.statusText);
        error.response = response;
        throw error;
    }
}