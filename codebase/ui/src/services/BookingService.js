/**
 * Created by franziskah on 02.11.16.
 */
import StorageService from "./StorageService";
import Util from "./Util";

module.exports = {
    /**
     * Book the seminar with the given id
     * @param id
     */
    bookSeminar(id){
        let token = StorageService.getUserToken();
        let userName = StorageService.getCurrentUser().userName;
        return fetch(Util.getBasicBookingPath(), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify({
                seminarId: id,
                userName: userName
            })
        }).then(Util.checkStatus).then(parseJson)
    },

    /**
     *
     * @param bookingId the id of the booking
     * @returns {Promise.<TResult>}
     */
    grantSingleBooking(bookingId){
        let token = StorageService.getUserToken();
        let userName = StorageService.getCurrentUser().userName;
        return fetch(Util.getBasicBookingPath() + '/' + bookingId + '/grant', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Auth-Token': token
            },
            body: JSON.stringify({
                seminarId: bookingId,
                userName: userName
            })
        }).then(Util.checkStatus).then(Util.parseJson)
    },
    grantAllBookings(employee){
        let token = StorageService.getUserToken();
        let userName = StorageService.getCurrentUser().userName;
        var result;
        employee[0].bookings.map(booking => {
            result = fetch(Util.getBasicBookingPath() + '/' + booking.id + '/grant', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-Auth-Token': token
                },
                body: JSON.stringify({
                    seminarId: booking.id,
                    userName: userName
                })
            }).then(Util.checkStatus).then(Util.parseJson)
        })
        return result;
    }
};


/**
 * @param response the response to parse
 * @returns {*} the response, parsed to json
 */
function parseJson(response) {
    return response.json();
}
