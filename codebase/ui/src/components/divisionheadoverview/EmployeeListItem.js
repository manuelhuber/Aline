/**
 * Created by Zebrata on 30.11.2016.
 */
import React from 'react';
import {Link} from 'react-router'
import Paper from 'material-ui/Paper';

export class EmployeeListItem extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        console.log(this.props.employee)
        return (
            <Link to={`employees/${this.props.employee.username}`}>
                <Paper className="employee-tile" zDepth={1}>
                    <div className="name">{this.props.employee.firstName} , {this.props.employee.lastName}</div>
                </Paper>
            </Link>
        );
    }

}