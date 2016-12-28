import React from 'react';
import {Link} from 'react-router'
import UserService from '../../services/UserService';
import Divider from 'material-ui/Divider';
import FlatButton from 'material-ui/FlatButton';
import FontIcon from 'material-ui/FontIcon';
import Paper from 'material-ui/Paper';
import Checkbox from 'material-ui/Checkbox';

export class Profile extends React.Component {
    constructor() {
        super();
        this.saveUser = this.saveUser.bind(this);
        this.navigateToSeminarHistory = this.navigateToSeminarHistory.bind(this);
        this.state = {
            ownProfile: false,
            firstName: '',
            lastName: '',
            division: '',
            authorities: '',
            bookings: []
        }
    }

    componentDidMount() {
        var currentUser;
        //Show other user
        if (this.props.location.query.userName) {
            currentUser = UserService.getUser(this.props.location.query.userName);
        }
        //Show own profile
        else {
            currentUser = UserService.getUser();
            this.setState({
                ownProfile: true
            })
        }
        currentUser.then(
            result => {
                this.saveUser(result);
            },
            failureResult => {
                this.props.router.replace('/error');
            }
        );
    }

    saveUser(userData) {
        this.setState({
            firstName: userData.firstName,
            lastName: userData.lastName,
            division: userData.division,
            authorities: userData.authorities,
            bookings: userData.bookings
        })
    }

    navigateToSeminarHistory() {

    }

    /**
     * Booking state can be " DENIED, REQUESTED, or GRANTED
     */
    renderSingleBooking(booking) {
        return (
            <Paper className="booking" zDepth={1}>
                <div className="name">{booking.seminarId}</div>
                <div className="date">{ new Date(booking.created).toLocaleDateString()}</div>
                <div className="status"><Checkbox label="Bestätigt" value={booking.status} disabled="true"/></div>
            </Paper>
        )
    }

    render() {
        return (
            <div>
                <main className="profile">
                    {this.state.ownProfile &&
                    <h2>Profil</h2>
                    }
                    {!(this.state.ownProfile) &&
                    <h2>{this.state.firstName} {this.state.lastName}</h2>
                    }
                    <h3>Gesamtkosten</h3>
                    <div className="output-properties">
                        <div className="property">
                            <output>
                                <label>Vergangene Seminare: (erfolgreich stattgefunden)</label>
                                2000,00 €
                            </output>
                        </div>
                        <div className="property">
                            <output>
                                <label>Geplante Seminare: (sowohl unbestätigt wie bestätigt)</label>
                                2000,00 €
                            </output>
                        </div>
                    </div>
                    <div className="bookings">
                        <h3>Meine Buchungen ( {new Date().getFullYear()} )</h3>
                        <span className="history-button">
                            <FlatButton onClick={this.navigateToSeminarHistory} label="Buchungshistorie"
                                        labelPosition="before"
                                        icon={<FontIcon
                                            className="material-icons">subdirectory_arrow_right</FontIcon>}/>
                        </span>
                        {this.state.bookings.map(this.renderSingleBooking)}
                    </div>
                </main>
            </div>
        );
    }

}