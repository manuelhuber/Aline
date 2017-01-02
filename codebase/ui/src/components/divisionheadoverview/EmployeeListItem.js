/**
 * Created by Zebrata on 30.11.2016.
 */
import React from 'react';
import {Link} from 'react-router'
import Paper from 'material-ui/Paper';
import {Popover} from 'material-ui/Popover';
import FlatButton from 'material-ui/FlatButton';
import Menu from 'material-ui/Menu';
import MenuItem from 'material-ui/MenuItem';
import BookingService from '../../services/BookingService';

export class EmployeeListItem extends React.Component {
    constructor(props) {
        super(props);
        this.showBookingList = this.showBookingList.bind(this);
        this.closeBookingList = this.closeBookingList.bind(this);
        this.navigateToProfile = this.navigateToProfile.bind(this);
        this.renderSingleBooking = this.renderSingleBooking.bind(this);
        this.confirmSingleBooking = this.confirmSingleBooking.bind(this);
        this.state = {
            bookingListOpen: false
        };
        //todo nur unbestätigte Buchungen werden im Popup gezeigt
        //todo Änderungen von Manu einpflegen
    }

    closeBookingList() {
        this.setState({
            bookingListOpen: false
        })
    }

    showBookingList(event) {
        this.setState({
            bookingListOpen: true,
            anchorEl: event.currentTarget
        });
    }

    navigateToProfile() {
        this.props.router.replace(
            {
                pathname: '/profile',
                query: {userName: this.props.employee.username},
            }
        )
    }

    confirmSingleBooking(bookingId) {
        var response = BookingService.grantSingleBooking(bookingId);
        response.then(
            result => {
                this.props.showSnackbar('Seminarbuchung erfolgreich bestätigt.');
            },
            failureResult => {
                this.props.router.replace('/error');
            }
        );
    }

    renderSingleBooking(booking) {
        return (
            <MenuItem primaryText={(new Date(booking.created).toLocaleDateString()) + ' für ' + booking.seminarId}
                      onClick={()=>{this.confirmSingleBooking(booking.id)}}
                      title="Nur dieses Seminar bestätigen"/>
        )
    }

    render() {
        return (
            <Paper className="employee" zDepth={1}>
                <div className="name" onClick={this.navigateToProfile}
                     title={'Zum Profil von ' + this.props.employee.firstName + ' ' + this.props.employee.lastName + ' navigieren'}>
                    <i className="material-icons md-light">face</i>
                    {this.props.employee.firstName}, {this.props.employee.lastName}
                </div>
                <div className="seminar-proof">
                    <FlatButton label="Buchungen bestätigen" onMouseOver={this.showBookingList}
                                disabled={this.props.employee.bookings.length < 1}
                                title="Alle offenen Buchungen bestätigen" id="seminar-lable"/>
                    {(this.props.employee.bookings.length > 0) &&
                    <Popover open={this.state.bookingListOpen} anchorEl={this.state.anchorEl}
                             anchorOrigin={{horizontal: 'left', vertical: 'bottom'}}
                             targetOrigin={{horizontal: 'left', vertical: 'top'}}
                             onRequestClose={this.closeBookingList}>
                        <Menu>
                            {this.props.employee.bookings.map(this.renderSingleBooking)}
                        </Menu>
                    </Popover>
                    }
                </div>
            </Paper>
        );
    }
}