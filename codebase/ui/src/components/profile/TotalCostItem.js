import React from 'react';

export class TotalCostItem extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div className="output-properties total-cost-item">
                {/*The total cost of all seminars that have already taken place*/}
                <div className="property">
                    <output title="Die summierten Kosten aller erfolgreich stattgefundener Seminare.">
                        <label>Absolute Gesamtkosten</label>
                        {this.props.issuedSpending} €
                    </output>
                </div>
                {/*The total cost of all granted & requested seminars*/}
                <div className="property">
                    <output title="Die Gesamtkosten aller bestätigten sowie unbestätigten Seminare.">
                        <label>Vorraussichtliche Gesamtkosten</label>
                        {this.props.plannedTotalSpending} €
                    </output>
                </div>
                {/*The total cost of all granted seminars*/}
                <div className="property">
                    <output title="Die Kosten aller bestätigten Seminare (sowohl vergangene, wie in der Zukunft liegende Seminare)">
                        <label>Kosten bestätigter Seminare (vergangen wie noch stattfindend)</label>
                        {this.props.grantedSpending} €
                    </output>
                </div>
                {/*The total cost of all granted & requested seminars that have not taken place yet*/}
                <div className="property">
                    <output title="Die Kosten aller in der Zukunft liegender Seminare (sowohl bestätigt, wie unbestätigt)">
                        <label>Voraussichtlich zukünftige Kosten (bestätigt wie unbestätigt)</label>
                        {this.props.plannedAdditionalSpending} €
                    </output>
                </div>
            </div>
        );
    }

}