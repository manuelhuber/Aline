import React from 'react';

export class Seminar extends React.Component {
    constructor(props) {
        super(props);
        this.handleBooking = this.handleBooking.bind(this);
    }

    handleBooking() {
        this.props.onSeminarBooking(this.props.seminar.name);
        this.forceUpdate();
    }

    render() {
        return (
            <div className="seminar">
                <div className="seminar-name">
                    <label htmlFor="seminarName">Name: </label>
                    <output type="text" id="seminarName">{this.props.seminar.name}</output>
                </div>
                <div className="seminar-description">
                    <label htmlFor="seminarDescription">Beschreibung: </label>
                    <output type="text" id="seminarDescription">{this.props.seminar.description}</output>
                </div>
                <div className="seminar-type">
                    <label htmlFor="seminarType">Typ: </label>
                    <output type="text" id="seminarType">{this.props.seminar.type}</output>
                </div>
                <div className="seminar-booked">
                    <label htmlFor="seminarBooked">Gebucht: </label>
                    <input type="checkbox" checked={this.props.seminar.booked} id="seminarBooked"/>
                </div>
                <button className="seminar-button" onClick={this.handleBooking}
                        disabled={this.props.seminar.booked}>Buchen
                </button>
            </div>
        );
    }
}

