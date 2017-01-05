import React from 'react';

export class TotalCostItem extends React.Component {
    constructor() {
        super();
        this.state = {
            grantedSpendingFormatted: 0,
            plannedSpendingFormatted: 0,
            sumFormatted: 0
        }
    }

    componentDidMount() {
        //Format the spending amount, because the backend hands it over in cent
        this.setState({
            grantedSpendingFormatted: (this.props.grantedSpending) / 100,
            plannedSpendingFormatted: (this.props.plannedSpending) / 100,
            sumFormatted: (this.props.grantedSpending + this.props.plannedSpending) / 100
        })
    }

    render() {
        return (
            <div className="output-properties total-cost-item">
                <div className="property past">
                    <output>
                        <label>Vergangen (erfolgreich stattgefunden)</label>
                        {this.state.grantedSpendingFormatted} €
                    </output>
                </div>
                <span className="symbol">+</span>
                <div className="property future">
                    <output>
                        <label>Geplant (sowohl unbestätigt wie bestätigt)</label>
                        {this.state.plannedSpendingFormatted} €
                    </output>
                </div>
                <span className="symbol">=</span>
                <div className="property sum">
                    <output>
                        <label>Gesamt</label>
                        {this.state.sumFormatted} €
                    </output>
                </div>
            </div>
        );
    }

}