import React from 'react';
import {Link} from 'react-router'

export class SeminarListItem extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="seminar">
                <div className="seminar-name">
                    <label htmlFor="seminarName">Name: </label>
                    <output type="text" id="seminarName">{this.props.seminar}</output>
                </div>
                <div><Link to={`seminars/${this.props.seminar}`}>Zur Detailseite...</Link></div>
            </div>
        );
    }
}
SeminarListItem.propTypes = {
    seminar: React.PropTypes.string
};

