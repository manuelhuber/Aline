import React from 'react';
import SeminarService from '../../../services/SeminarService';
import {Participants} from './Participants';

export class PrintableInvoice extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seminar: {},
            participants: [],
            participantCount: 0,
            totalCost: 0,
            divisionSums: []
        }
    }

    componentDidMount() {
        let bill = SeminarService.generateBillForSeminar(this.props.params.seminarId);
        bill.then(
            result => {
                this.setState({
                    seminar: result.seminar,
                    participants: result.participants,
                    participantCount: result.participantCount,
                    totalCost: result.totalCost,
                    divisionSums: result.divisionSums
                })
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    render() {
        return (
            <div className="invoice">
                Your Invoice for the seminar with id {this.props.params.seminarId} here
                {/*Tabelle mit Spalten "Nutzer" "Bereich" "Betrag"*/}
                <h3>Teilnehmerliste</h3>
                <Participants participants={this.state.participants}
                              costsPerParticipant={this.state.seminar.costsPerParticipant}/>
                <h3>Kosten pro Bereich</h3>
            </div>
        )
    }
}

