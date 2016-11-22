import React from 'react';
import SeminarService from '../../services/SeminarService';

export class SeminarDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seminar: {}
        };
        this.handleBooking = this.handleBooking.bind(this);
        this.saveSeminar = this.saveSeminar.bind(this);
    }

    componentDidMount() {
        let seminar = SeminarService.getSeminarById(this.props.params.seminarName);
        seminar.then(
            result => {
                this.saveSeminar(result)
            }
        );
    }

    saveSeminar(result) {
        this.setState({
            seminar: result
        })
    }

    handleBooking() {
        //todo
    }

    render() {
        return (
            <div className="seminar">
                <div className="seminar-name">
                    <label htmlFor="seminarName">Name: </label>
                    <output type="text" id="seminarName">{this.state.seminar.name}</output>
                </div>
                <div className="seminar-description">
                    <label htmlFor="seminarDescription">Beschreibung: </label>
                    <output type="text" id="seminarDescription">{this.state.seminar.description}</output>
                </div>
                <div className="seminar-trainer">
                    <label htmlFor="seminarTrainer">Typ: </label>
                    <output type="text" id="seminarTrainer">{this.state.seminar.trainer}</output>
                </div>
                <button className="seminar-button" onClick={this.handleBooking}
                        disabled={this.state.seminar.booked}>Buchen
                </button>
            </div>
        );
    }
}

