/**
 * Created by Zebrata on 30.11.2016.
 */
import React from 'react';
import {Link} from 'react-router'
import Paper from 'material-ui/Paper';

export class Employee extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Link to={`seminars/${this.props.employee.id}`}>
                <Paper className="employee-tile" zDepth={1}>
                    <div className="name">{this.props.employee.firstname}</div>
                    <div className="trainer">{this.props.employee.lastname}</div>
                </Paper>
            </Link>
        );
    }

}