import React from 'react';
import FlatButton from 'material-ui/FlatButton';
import FontIcon from 'material-ui/FontIcon';
import {BookingSummaryItem} from './BookingSummaryItem';
import UserService from '../../services/UserService';

export class BookingHistory extends React.Component {
    constructor() {
        super();
        this.navigateBack = this.navigateBack.bind(this);
        this.removeCurrentYearsBookings = this.removeCurrentYearsBookings.bind(this);
        this.state = {
            bookings: []
        }
    }

    componentDidMount() {
        let currentUser = UserService.getUser(this.props.location.query.userName);
        currentUser.then(
            result => {
                let filteredBookings = this.removeCurrentYearsBookings(userData.bookings);
                this.setState({
                    bookings: filteredBookings
                })
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    removeCurrentYearsBookings(bookings){
        let filteredBookings = bookings;
        let bookingsOfCurrentYear = filteredBookings.find( (booking)=>{return booking.year === new Date().getFullYear()});
        let indexOfBookingsOfCurrentYear = filteredBookings.indexOf(bookingsOfCurrentYear);
        filteredBookings.splice(indexOfBookingsOfCurrentYear, 1);
        return filteredBookings;
    }

    navigateBack() {
        this.props.router.replace('/profile');
    }

    render() {
        return (
            <div>
                <main className="booking-history">
                    <span className="back-button">
                            <FlatButton onClick={this.navigateBack} label="Zurück zum Profil"
                                        icon={<FontIcon className="material-icons">navigate_before</FontIcon>}/>
                    </span>
                    {this.state.bookings.map((bookingSummary)=> {
                        return <BookingSummaryItem bookingSummary={bookingSummary}/>
                    })
                    }
                    {this.state.bookings.length < 1 &&
                    <span>Keine Historie vorhanden.</span>
                    }
                </main>
            </div>
        );
    }

}