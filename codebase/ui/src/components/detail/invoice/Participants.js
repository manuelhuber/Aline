import React from 'react';
import Util from '../../../services/Util';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

export class Participants extends React.Component {
    constructor(props) {
        super(props);
        this.renderRow = this.renderRow.bind(this);
    }

    renderRow(participant) {
        return (
            <TableRow key={participant.userName}>
                <TableRowColumn
                    title={participant.firstName + ' ' + participant.lastName}>{participant.firstName + ' ' + participant.lastName}</TableRowColumn>
                <TableRowColumn title={participant.division}>{participant.division}</TableRowColumn>
                <TableRowColumn
                    title={Util.formatMoneyFromCent(this.props.costsPerParticipant) + '€'}>{Util.formatMoneyFromCent(this.props.costsPerParticipant)}€</TableRowColumn>
            </TableRow>
        )
    }

    render() {
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
                    { this.props.participants.length > 0 &&
                    this.props.participants.map(this.renderRow)
                    }
                    { this.props.participants.length < 1 &&
                    <div className="no-participants">Keine Teilnehmer vorhanden.</div>
                    }
                </TableBody>
            </Table>
        )
    }
}

