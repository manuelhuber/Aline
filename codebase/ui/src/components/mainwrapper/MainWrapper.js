/**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';

export class SeminarDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seminar: {
                name: 'Java',
                description: 'Lerne Java!',
                type: 'Coding',
                booked: false
            }
        };
        this.handleBooking = this.handleBooking.bind(this);
        //todo load seminar for this.props.params.seminarName
    }

    handleBooking() {
        var updatedSeminar = this.state.seminar;
        updatedSeminar.booked = true;
        this.setState = {
            seminar: updatedSeminar
        };
        this.forceUpdate();
    }

    render() {
        return (
            <div className="seminar">
                <div>Routing got me this name: {this.props.params.seminarName}</div>
                <div className="seminar-name">
                    <label htmlFor="seminarName">Name: </label>
                    <output type="text" id="seminarName">{this.state.seminar.name}</output>
                </div>
                <div className="seminar-description">
                    <label htmlFor="seminarDescription">Beschreibung: </label>
                    <output type="text" id="seminarDescription">{this.state.seminar.description}</output>
                </div>
                <div className="seminar-type">
                    <label htmlFor="seminarType">Typ: </label>
                    <output type="text" id="seminarType">{this.state.seminar.type}</output>
                </div>
                <div className="seminar-booked">
                    <label htmlFor="seminarBooked">Gebucht: </label>
                    <input type="checkbox" checked={this.state.seminar.booked} id="seminarBooked"/>
                </div>
                <button className="seminar-button" onClick={this.handleBooking}
                        disabled={this.state.seminar.booked}>Buchen
                </button>
            </div>
        );
    }
}

