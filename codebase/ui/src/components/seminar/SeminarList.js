import {Seminar} from './Seminar';
import React from 'react';

export class SeminarList extends React.Component {
    constructor() {
        super();
        this.handleSeminarBooking = this.handleSeminarBooking.bind(this);
        this.renderSeminar = this.renderSeminar.bind(this);
        this.state = {
            seminars: []
        };
    }

    componentDidMount() {
        this.setState({
            seminars: [
                {
                    name: 'Java',
                    description: 'Lerne Java!',
                    type: 'Coding',
                    booked: false
                },
                {
                    name: 'Lesen',
                    description: 'Lerne Lesen!',
                    type: 'No-Coding',
                    booked: false
                }
            ]
        })
    }

    handleSeminarBooking(seminarName) {
        let updatedSeminars = [];
        this.state.seminars.forEach(
            seminar => {
                if (seminar.name == seminarName) {
                    seminar.booked = true;
                }
                updatedSeminars.push(seminar);
            }
        );
        this.setState = {
            seminars: updatedSeminars
        }
    }

    render() {
        return (
            <div>
                <h2>Mögliche Seminare:</h2>
                <div className="seminar-list">
                    { this.state.seminars.map(this.renderSeminar) }
                </div>
            </div>
        );
    }

    renderSeminar(currentSeminar) {
        return <Seminar seminar={currentSeminar}
                        key={currentSeminar.name}
                        onSeminarBooking={this.handleSeminarBooking}/>;
    }
}