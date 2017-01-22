import React from 'react';
import SeminarService from '../../../services/SeminarService';
import Util from '../../../services/Util';
import {Participants} from './Participants';
import {Divisions} from './Divisions';

export class PrintableInvoice extends React.Component {
    constructor(props) {
        super(props);
        this.renderCosts = this.renderCosts.bind(this);
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

    renderCosts() {
        return (
            <div className="costs">
                <div>
                    <output title="Gesamtkosten">
                        <label>Gesamtkosten</label>
                        <span className="money-amount">{Util.formatMoneyFromCent(this.state.totalCost)} €</span>
                    </output>
                </div>
                <div>
                    <output title="Anzahl Teilnehmer am Seminar">
                        <label>TN gesamt</label>
                        <span className="money-amount participant-count">{this.state.participantCount}</span>
                    </output>
                </div>
                <div >
                    <output title="Kosten pro Teilnehmer am Seminar">
                        <label>Kosten/TN</label>
                        <span className="money-amount">{Util.formatMoneyFromCent(this.state.seminar.costsPerParticipant)} €</span>
                    </output>
                </div>
            </div>
        )
    }

    render() {
        return (
            <div className="invoice">
                Your Invoice for the seminar with id {this.props.params.seminarId} here
                <h3>Teilnehmerliste</h3>
                <Participants participants={this.state.participants}
                              costsPerParticipant={this.state.seminar.costsPerParticipant}/>
                <h3>Kosten</h3>
                {this.renderCosts()}
                <h3>Kosten pro Bereich</h3>
                <Divisions divisionSums={this.state.divisionSums}/>
            </div>
        )
    }
}

