import React from 'react';
import {Link} from 'react-router'

export class SeminarListItem extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="seminar-tile">
                <Link to={`seminars/${this.props.seminar.id}`}>
                    <div className="name">
                        <output type="text">{this.props.seminar.name}</output>
                    </div>
                </Link>
            </div>
        );
    }
}