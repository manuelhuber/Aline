import React from 'react';
import {Link} from 'react-router'

export class SeminarListItem extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="seminar-tile">
                <Link to={`seminars/${this.props.seminar}`}>
                    <div className="name">
                        <output type="text">{this.props.seminar}</output>
                    </div>
                </Link>
            </div>
        );
    }
}
SeminarListItem.propTypes = {
    seminar: React.PropTypes.string
};

