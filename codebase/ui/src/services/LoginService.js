/**
 * Created by franziskah on 02.11.16.
 */
import  "./StorageService";
import {storeUserToken, storeCurrentUser, getUserToken} from "./StorageService";

export function loginUser(userName, userPwd) {
    fetch('http://localhost:8008/api/auth', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: userName,
            password: userPwd,
        })
    }).then(parseJson).then(result => {
        storeUserToken(result.token);
        storeCurrentUser(userName, result.authorities);
    })
}

export function seminarService() {
    fetch('http://localhost:8008/api/protected', {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'X-Auth-Token': getUserToken()
        }
    }).then(parseJson).then(result => console.log(result))
}

/**
 * @param response the response to parse
 * @returns {*} the response, parsed to json
 */
function parseJson(response) {
    return response.json();
}

