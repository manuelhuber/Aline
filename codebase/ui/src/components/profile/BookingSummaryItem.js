/**
 * A bookingSummary is the booking summary of a whole year, containing
 * the single bookings and some infos about the year
 */
import React from 'react';
import {Link} from 'react-router'
import Paper from 'material-ui/Paper';
import Checkbox from 'material-ui/Checkbox';
import {TotalCostItem} from './TotalCostItem';

export class BookingSummaryItem extends React.Component {
    constructor() {
        super();
        this.renderSingleBooking = this.renderSingleBooking.bind(this);
    }

    renderSingleBooking(booking) {
        return (
            <Paper className="booking" zDepth={1}>
                <Link to={`seminars/${booking.seminarId}`} title={'Zum Seminar "' + booking.seminarName + '" navigieren'}>
                    <div className="name">Seminar: {booking.seminarName}</div>
                </Link>
                <div className="date">Gebucht am: { new Date(booking.created).toLocaleDateString()}</div>
                <div className="date">Aktualisiert am: { new Date(booking.updated).toLocaleDateString()}</div>
                <div className="status">
                    <Checkbox label="Bestätigt" checked={(booking.status == 'GRANTED')} disabled={true}/>
                </div>
            </Paper>
        )
    }

    render() {
        return (
            <div className="bookings summary-item">
                <h3>{this.props.bookingSummary.year}</h3>
                <TotalCostItem grantedSpending={this.props.bookingSummary.grantedSpending}
                               plannedSpending={this.props.bookingSummary.plannedSpending}/>
                {this.props.bookingSummary.bookings.map(this.renderSingleBooking)}
            </div>
        )
    }

}