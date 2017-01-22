import React from 'react';
import Util from '../../../services/Util';

export class Participants extends React.Component {
    constructor(props) {
        super(props);
        this.renderRow = this.renderRow.bind(this);
    }

    renderRow(participant) {
        return (
            <tr key={participant.userName}>
                <td title={participant.firstName + ' ' + participant.lastName}>{participant.firstName + ' ' + participant.lastName}</td>
                <td title={participant.division}>{participant.division}</td>
            </tr>
        )
    }

    render() {
        if (this.props.participants.length < 1) {
            return (
                <div>Keine Teilnehmer vorhanden</div>
            )
        }
        else {
            return (
                <table>
                    <thead>
                    <tr>
                        <th>Teilnehmer</th>
                        <th>Bereich</th>
                    </tr>
                    </thead>
                    <tbody>
                    { this.props.participants.map(this.renderRow)}
                    </tbody>
                </table>
            )
        }
    }
}

