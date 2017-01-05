import React from 'react';
import {Link} from 'react-router'
import Paper from 'material-ui/Paper';

export class SeminarListItem extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Link to={`seminars/${this.props.seminar.id}`}>
                <Paper className={this.props.isPast ? 'seminar-tile past-seminar' : 'seminar-tile'} zDepth={1}>
                    <div className="name">{this.props.seminar.name}</div>
                    <div className="trainer">{this.props.seminar.trainer}</div>
                </Paper>
            </Link>
        );
    }
}