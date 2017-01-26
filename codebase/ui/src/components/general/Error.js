import React from 'react';

export class Error extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount(){
        this.props.showLoadingIndicator(false);
    }

    render() {
        return (
            <div className="error-page">
                <h2>Autsch.</h2>
                <p>Da ist leider was schief gelaufen. Das tut uns leid. Sollte der Fehler erneut auftreten, wende dich bitte an den zuständigen Admin.</p>
            </div>
        );
    }
}

