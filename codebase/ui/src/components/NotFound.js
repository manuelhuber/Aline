import React from 'react';

export class NotFound extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="not-found">
                <h2>Sorry. Cannot be found.</h2>
            </div>
        );
    }
}

