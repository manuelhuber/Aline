import React from 'react';
import Util from '../../../services/Util';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';

export class Divisions extends React.Component {
    constructor(props) {
        super(props);
        this.renderRow = this.renderRow.bind(this);
    }

    renderRow(division) {
        return (
            <TableRow key={division.division}>
                <TableRowColumn title={division.division}>{division.division}</TableRowColumn>
                <TableRowColumn
                    title={Util.formatMoneyFromCent(division.totalCost) + '€'}>{Util.formatMoneyFromCent(division.totalCost)}€</TableRowColumn>
            </TableRow>
        )
    }

    render() {
        return (
            <Table multiSelectable={false} selectable={false}>
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                        <TableHeaderColumn>Bereich</TableHeaderColumn>
                        <TableHeaderColumn>Betrag</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false}>
                    { this.props.divisionSums.length > 0 &&
                    this.props.divisionSums.map(this.renderRow)
                    }
                    { this.props.divisionSums.length < 1 &&
                    <div className="no-divisions">Keine Bereiche vorhanden.</div>
                    }
                </TableBody>
            </Table>
        )
    }
}

