import React from 'react';
import UserService from '../../services/UserService';
import FlatButton from 'material-ui/FlatButton';
import FontIcon from 'material-ui/FontIcon';
import {BookingSummaryItem} from './BookingSummaryItem';

export class Profile extends React.Component {
    constructor() {
        super();
        this.saveUser = this.saveUser.bind(this);
        this.getCurrentYearsBookingSummary = this.getCurrentYearsBookingSummary.bind(this);
        this.navigateToSeminarHistory = this.navigateToSeminarHistory.bind(this);
        this.state = {
            ownProfile: false,
            firstName: '',
            lastName: '',
            division: '',
            authorities: '',
            bookings: [],
            currentYearsBookingSummary: {}
        }
    }

    componentDidMount() {
        var currentUser;
        //Show profile for: other user
        if (this.props.location.query.userName) {
            currentUser = UserService.getUser(this.props.location.query.userName);
        }
        //Show profile for: owner
        else {
            currentUser = UserService.getUser();
            this.setState({
                ownProfile: true
            })
        }
        //Save User in either cases
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
        let currentYearsBookingSummary = this.getCurrentYearsBookingSummary(userData.bookings);
        this.setState({
            firstName: userData.firstName,
            lastName: userData.lastName,
            division: userData.division,
            authorities: userData.authorities,
            bookings: userData.bookings,
            currentYearsBookingSummary: currentYearsBookingSummary
        });
        console.log('Saved userData: ' + userData);
    }

    navigateToSeminarHistory() {
        this.props.router.replace('/history');
    }

    getCurrentYearsBookingSummary(bookings) {
        return bookings.find((booking)=> {
            return booking.year === (new Date().getFullYear());
        });
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
                    {this.state.currentYearsBookingSummary.year &&
                    <span>
                        <div className="bookings">
                            <h3>Meine Buchungen</h3>
                            {this.state.ownProfile &&
                            <span className="history-button">
                                <FlatButton onClick={this.navigateToSeminarHistory} label="Buchungshistorie"
                                            title={this.state.bookings.length <=1? 'Keine Buchungshistorie vorhanden': 'Zur Buchungshistorie navigieren'}
                                            labelPosition="before" disabled={this.state.bookings.length <= 1}
                                            icon={<FontIcon className="material-icons">subdirectory_arrow_right</FontIcon>}/>
                            </span>
                            }
                            {/* Show the current years bookings */}
                            <BookingSummaryItem bookingSummary={this.state.currentYearsBookingSummary}/>
                        </div>
                    </span>
                    }
                    {!this.state.currentYearsBookingSummary.year && <span>Keine Buchungen vorhanden.</span>}
                </main>
            </div>
        );
    }

}