import React from 'react';
import SeminarService from '../../services/SeminarService';
import Util from '../../services/Util';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

export class PrintableInvoice extends React.Component {
    constructor(props) {
        super(props);
        //Participants
        this.renderParticipants = this.renderParticipants.bind(this);
        this.renderRow = this.renderRow.bind(this);
        this.state = {
            seminar: {}, //costsPerParticipant
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

    renderRow(participant) {
        return (
            <TableRow key={participant.userName}>
                <TableRowColumn
                    title={participant.firstName + ' ' + participant.lastName}>{participant.firstName + ' ' + participant.lastName}</TableRowColumn>
                <TableRowColumn title={participant.division}>{participant.division}</TableRowColumn>
                <TableRowColumn
                    title={Util.formatMoneyFromCent(this.state.seminar.costsPerParticipant) + '€'}>{Util.formatMoneyFromCent(this.state.seminar.costsPerParticipant)}€</TableRowColumn>
            </TableRow>
        )
    }

    renderParticipants() {
        return (
            <Table multiSelectable={false} selectable={false}>
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                        <TableHeaderColumn>Teilnehmer</TableHeaderColumn>
                        <TableHeaderColumn>Bereich</TableHeaderColumn>
                        <TableHeaderColumn>Betrag</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false}>
                    { this.state.participants.length > 0 &&
                    this.state.participants.map(this.renderRow)
                    }
                    { this.state.participants.length < 1 &&
                    <div className="no-participants">Keine Teilnehmer vorhanden.</div>
                    }
                </TableBody>
            </Table>
        )
    }

    render() {
        return (
            <div className="invoice">
                Your Invoice for the seminar with id {this.props.params.seminarId} here
                {/*Tabelle mit Spalten "Nutzer" "Bereich" "Betrag"*/}
                <h3>Teilnehmerliste</h3>
                {this.renderParticipants()}
            </div>
        )
    }
}

