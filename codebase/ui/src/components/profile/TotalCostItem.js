import React from 'react';
import Util from '../../services/Util';

export class TotalCostItem extends React.Component {
    constructor() {
        super();
    }

    render() {
        if (this.props.isHistory) {
            return (
                <div className="output-properties total-cost-item">
                    <div className="property">
                        <output title="Die Gesamtausgaben des Jahres.">
                            <label>Ausgaben</label>
                            {Util.formatMoneyFromCent(this.props.issuedSpending)} €
                        </output>
                    </div>
                </div>
            )
        }
        else {
            return (
                <div className="output-properties total-cost-item">
                    {/*The total cost of all seminars that have already taken place*/}
                    <div className="property">
                        <output title="Die summierten Kosten aller erfolgreich stattgefundener Seminare.">
                            <label>Bereits ausgegeben</label>
                            {Util.formatMoneyFromCent(this.props.issuedSpending)} €
                        </output>
                    </div>
                    {/*The total cost of all granted & requested seminars*/}
                    <div className="property">
                        <output title="Die Gesamtkosten aller bestätigten sowie unbestätigten Seminare.">
                            <label>Vorraussichtliche Gesamtkosten</label>
                            {Util.formatMoneyFromCent(this.props.plannedTotalSpending)} €
                        </output>
                    </div>
                </div>
            );
        }
    }

}