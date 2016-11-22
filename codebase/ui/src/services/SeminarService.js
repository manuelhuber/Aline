/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";

module.exports = {

    getAllSeminars() {
        fetch('http://localhost:8008/api/seminar', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': StorageService.getUserToken()
            },
            body: {}
        }).then(parseJson).then(result => {
            return result
        })
    },

    getSeminarById(seminarId){
        fetch('http://localhost:8008/api/seminar/' + seminarId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': StorageService.getUserToken()
            }
        }).then(parseJson).then(result => {
            return result
        })
    },

    addSeminar(seminar){
        fetch('http://localhost:8008/api/seminar', {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'X-Auth-Token': StorageService.getUserToken()
            },
            body: JSON.stringify(seminar)
        }).then(parseJson).then(result => {
            console.log(result)
        })
    }

};

/**
 * @param response the response to parse
 * @returns {*} the response, parsed to json
 */
function parseJson(response) {
    return response.json();
}