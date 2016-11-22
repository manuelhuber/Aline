import React from 'react';

export class NotFound extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="not-found">
                <h3>Entschuldige. Die von dir angeforderte Seite konnte nicht gefunden werden.
                </h3>
            </div>
        );
    }
}

