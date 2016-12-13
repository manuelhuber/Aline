const BASIC_PATH = 'http://localhost:8008/api';

module.exports = {

    /**
     * @returns {string} the basic path of the REST api
     */
    getBasicPath(){
        return BASIC_PATH
    },

    /**
     * @returns {string} the basic seminars path of the REST api
     */
    getBasicSeminarsPath(){
        return BASIC_PATH + '/seminars'
    },

    /**
     * @param response the response to parse
     * @returns {*} the response, parsed to json
     */
    parseJson(response) {
        return response.json();
    },

    /**
     * Check the status of the response
     * @param response the response got by the server
     */
    checkStatus(response) {
        if (!response.ok) {
            console.log('Der Response des Servers beim Aufruf der addSeminar Methode war nicht in Ordnung:' + response);
        }
    }
};
