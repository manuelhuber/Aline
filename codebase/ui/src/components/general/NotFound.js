import React from 'react';

export class NotFound extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="not-found">
                <h2>Seite nicht gefunden.</h2>
                <p>Die angeforderte Seite konnte nicht gefunden werden.</p>
            </div>
        );
    }
}

