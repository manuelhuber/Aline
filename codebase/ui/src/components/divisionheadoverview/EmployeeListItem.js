/**
 * Created by Zebrata on 30.11.2016.
 */
import React from 'react';
import {Link} from 'react-router'
import Paper from 'material-ui/Paper';
import {Popover} from 'material-ui/Popover';
import FlatButton from 'material-ui/FlatButton';

export class EmployeeListItem extends React.Component {
    constructor(props) {
        super(props);
        this.semList = this.semList(this);
        this.openSeminarList = this.openSeminarList.bind(this);
        this.closeSeminarList = this.closeSeminarList.bind(this);
        this.state = {
            semListOpen: false
        }

    }
    closeSeminarList() {
        this.setState({
            semListOpen: false
        })
    }
    openSeminarList(event) {
        this.setState({
            semListOpen: true,
        });
    }


    render() {
        return (
                <Paper className="employee" zDepth={1}>
                    <div className="name">
                        <i className="material-icons md-light">face</i>
                        {this.props.employee.firstName}, {this.props.employee.lastName}
                    </div>
                    <div className="seminar-proof">
                        <FlatButton label="Seminare bestätigen" onClick={this.openSeminarList} onMouseLeave={this.closeSeminarList} id="seminar-lable"/>
                        <Popover open={this.state.semListOpen}>
                            <h3>Test</h3>
                        </Popover>
                    </div>
                </Paper>
        );
    }
    semList(seminar) {
       return seminar.props.employee.bookings.map(function (semina) {
           return <p>{semina.seminarId}</p>;
       })
    }
}