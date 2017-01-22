/**
 * Created by Zebrata on 30.11.2016.
 */
import React from "react";
import {Link} from "react-router";
import Paper from "material-ui/Paper";
import {Popover} from "material-ui/Popover";
import FlatButton from "material-ui/FlatButton";
import Menu from "material-ui/Menu";
import MenuItem from "material-ui/MenuItem";

export class EmployeeListItem extends React.Component {
    constructor(props) {
        super(props);
        this.showBookingList = this.showBookingList.bind(this);
        this.closeBookingList = this.closeBookingList.bind(this);
        this.navigateToProfile = this.navigateToProfile.bind(this);
        this.renderSingleBooking = this.renderSingleBooking.bind(this);
        this.confirmSingleBooking = this.confirmSingleBooking.bind(this);
        this.state = {
            bookingListOpen: false,
        };
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

    confirmAllBookings(employeeBookings) {//this just calls a parent method so that parent state data can be renewed (especially for toggle)
        if (typeof this.props.confirmAllBookings === 'function') {
            var result = this.props.confirmAllBookings(employeeBookings);
        }
    }

    confirmSingleBooking(bookingId) { //this just calls a parent method so that parent state data can be renewed (especially for toggle)
        if (typeof this.props.confirmSingleBooking === 'function') {
            this.props.confirmSingleBooking(bookingId);
        }
    }

    checkForUngrantedBookings(bookings) {
        return this.props.checkForUngrantedBookings(bookings);
    }

    checkIfBookingIsAlreadyGranted(booking) {
        if (booking.bookings[0].status == 'GRANTED') {
            return true;
        }
        return false;
    }

    renderSingleBooking(booking) {
        return (
                <MenuItem
                    primaryText={(new Date(booking.bookings[0].created).toLocaleDateString()) + ' für ' + booking.bookings[0].seminarName}
                    onClick={()=> {
                        this.confirmSingleBooking(booking.bookings[0].id)
                    }}
                    title="Nur dieses Seminar bestätigen"
                    key={booking.bookings[0].id}
                    disabled={this.checkIfBookingIsAlreadyGranted(booking)}/>
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
                    <FlatButton label="Buchungsübersicht" onMouseOver={this.showBookingList}
                                disabled={this.checkForUngrantedBookings(this.props.employee.bookings)}
                                title="Alle offenen Buchungen bestätigen" id="seminar-lable"
                                labelStyle={{height: '50px'}}
                                onClick={()=> {
                                    this.confirmAllBookings(this.props.employee.bookings)
                                }}/>
                    {(this.props.employee.bookings.length > 0 && !this.checkForUngrantedBookings(this.props.employee.bookings)) &&
                    <Popover open={this.state.bookingListOpen} anchorEl={this.state.anchorEl}
                             anchorOrigin={{horizontal: 'middle', vertical: 'bottom'}}
                             targetOrigin={{horizontal: 'middle', vertical: 'top'}}
                             onRequestClose={this.closeBookingList}
                             disabled={this.checkForUngrantedBookings(this.props.employee.bookings)}>
                        <Menu>
                            <MenuItem
                                primaryText='Alle Seminare bestätigen'
                                onClick={()=> {
                                    this.confirmAllBookings(this.props.employee.bookings)
                                }}
                                title="Alle Seminare bestätigen"
                                key='999'/>
                            {this.props.employee.bookings.map(this.renderSingleBooking)}
                        </Menu>
                    </Popover>
                    }
                </div>
            </Paper>
        );
    }
}