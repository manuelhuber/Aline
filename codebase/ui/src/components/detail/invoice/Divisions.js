import React from 'react';
import Util from '../../../services/Util';

export class Divisions extends React.Component {
    constructor(props) {
        super(props);
        this.renderRow = this.renderRow.bind(this);
    }

    renderRow(division) {
        return (
            <tr key={division.division}>
                <td title={division.division}>{division.division}</td>
                <td title={Util.formatMoneyFromCent(division.totalCost) + '€'}>{Util.formatMoneyFromCent(division.totalCost)}€</td>
            </tr>
        )
    }

    render() {
        if (this.props.divisionSums.length < 1) {
            return (
                <div>Keine Bereiche vorhanden.</div>
            )
        }
       else{
            return (
                <table>
                    <thead>
                    <tr>
                        <th>Bereich</th>
                        <th>Betrag</th>
                    </tr>
                    </thead>
                    <tbody>
                    {  this.props.divisionSums.map(this.renderRow) }
                    </tbody>
                </table>
            )
        }
    }
}

