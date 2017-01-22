import React from 'react';
import SeminarService from '../../services/SeminarService';
import BookingService from '../../services/BookingService';
import StorageService from '../../services/StorageService';
import AuthService from '../../services/AuthService';
import RaisedButton from 'material-ui/RaisedButton';
import FlatButton from 'material-ui/FlatButton';
import Dialog from 'material-ui/Dialog';
import Chip from 'material-ui/Chip';
import {Link} from 'react-router'

var SeminarTexts = {
    description: 'Beschreibung',
    agenda: 'Agenda',
    category: 'Kategorie',
    requirements: 'Voraussetzungen',
    trainer: 'Referent/externer Anbieter',
    contactPerson: 'Ansprechpartner bei Interesse',
    trainingType: 'Schulungsformat',
    maximumParticipants: 'Maximale Teilnehmeranzahl',
    goal: 'Geplante Weiterentwicklungen',
    duration: 'Dauer',
    cycle: 'Turnus',
    bookingTimelog: 'Kontierung (im Timelog)'
};

export class SeminarDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seminar: {},
            bookingAlertOpen: false,
            deleteAlertOpen: false,
        };
        this.setSeminar = this.setSeminar.bind(this);
        this.handleSeminarUpdate = this.handleSeminarUpdate.bind(this);
        this.renderProperties = this.renderProperties.bind(this);
        this.renderDates = this.renderDates.bind(this);

        this.checkIfCurrentUserHasAlreadyBooked = this.checkIfCurrentUserHasAlreadyBooked.bind(this);
        this.maximumParticipantsAchieved = this.maximumParticipantsAchieved.bind(this);
        this.isBookable = this.isBookable.bind(this);
        //Booking
        this.handleBooking = this.handleBooking.bind(this);
        this.openBookingDialog = this.openBookingDialog.bind(this);
        this.closeBookingDialog = this.closeBookingDialog.bind(this);
        //Delete
        this.handleDelete = this.handleDelete.bind(this);
        this.openDeleteDialog = this.openDeleteDialog.bind(this);
        this.closeDeleteDialog = this.closeDeleteDialog.bind(this);
        this.redirectToOverview = this.redirectToOverview.bind(this);
    }

    componentDidMount() {
        let seminar = SeminarService.getSeminarById(this.props.params.seminarName);
        seminar.then(
            result => {
                this.setSeminar(result)
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    maximumParticipantsAchieved() {
        return this.state.seminar.activeBookings >= this.state.seminar.maximumParticipants;
    }

    checkIfCurrentUserHasAlreadyBooked() {
        var currentUser = StorageService.getCurrentUser().userName;
        if (this.state.seminar.bookings) {
            return this.state.seminar.bookings.find((element) => {
                return element.username === currentUser;
            });
        }
        return false;
    }

    isBookable() {
        return this.state.seminar.bookable;
    }

    setSeminar(result) {
        this.setState({
            seminar: result
        })
    }

    /**
     * Booking
     */
    handleBooking() {
        var response = BookingService.bookSeminar(this.state.seminar.id);
        response.then(
            result => {
                this.props.showSnackbar('Buchungsanfrage erfolgreich erstellt');
                this.closeBookingDialog();
                window.setTimeout(function () {
                    location.reload();
                }, 2000);
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    openBookingDialog() {
        this.setState({
            bookingAlertOpen: true
        })
    }

    closeBookingDialog() {
        this.setState({
            bookingAlertOpen: false
        })
    }

    /**
     * Delete
     */
    handleDelete() {
        var response = SeminarService.deleteSeminar(this.state.seminar.id);
        response.then(
            result => {
                this.closeDeleteDialog();
                this.props.showSnackbar('Seminar wurde erfolgreich gelöscht.');
                window.setTimeout(this.redirectToOverview, 2000);
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    redirectToOverview() {
        this.props.router.replace('/seminars');
    }

    openDeleteDialog() {
        this.setState({
            deleteAlertOpen: true
        })
    }

    closeDeleteDialog() {
        this.setState({
            deleteAlertOpen: false
        })
    }

    /**
     * Update
     */

    handleSeminarUpdate() {
        this.props.router.replace(
            {
                pathname: '/create',
                query: {changeExisting: this.state.seminar.id},
            }
        )
    }

    renderProperties(key) {
        return (
            <div className="property" key={key}>
                <output>
                    <label>{SeminarTexts[key]}</label>
                    {this.state.seminar[key]}
                </output>
            </div>
        )
    }

    renderDates(date) {
        let formattedDate = new Date(date).toLocaleDateString();
        return (
            <p key={formattedDate}>
                {formattedDate}
            </p>
        )
    }

    renderTargetLevels(targetLevel) {
        return (
            <p key={targetLevel}>
                {targetLevel}
            </p>
        )
    }

    render() {
        const bookingActions = [
            <FlatButton label="Abbrechen" onClick={this.closeBookingDialog}/>,
            <FlatButton label="Buchen" primary={true} onClick={this.handleBooking}/>
        ];
        const deleteActions = [
            <FlatButton label="Abbrechen" onClick={this.closeDeleteDialog}/>,
            <FlatButton label="Löschen" primary={true} onClick={this.handleDelete}/>
        ];

        return (
            <div className="seminar">
                <h2>{this.state.seminar.name}</h2>
                <div className="seminar-info-chips">
                    {this.checkIfCurrentUserHasAlreadyBooked() &&
                    <Chip backgroundColor="rgb(255, 64, 129)">Von dir gebucht</Chip>
                    }
                    {this.maximumParticipantsAchieved() &&
                    <Chip backgroundColor="rgb(255, 64, 129)">Maximale Teilnehmeranzahl erreicht</Chip>
                    }
                    {!this.isBookable() &&
                    <Chip backgroundColor="rgb(255, 64, 129)">Nicht buchbar</Chip>
                    }
                </div>

                { AuthService.isFrontOffice() &&
                <div className="button-wrapper frontoffice-buttons">
                    <RaisedButton label="Löschen" onClick={this.openDeleteDialog}>
                        <Dialog actions={deleteActions} modal={false} open={this.state.deleteAlertOpen}
                                onRequestClose={this.close}>
                            Möchtest du das Seminar <span className="highlight-text">"{this.state.seminar.name}" </span>
                            wirklich löschen?
                        </Dialog>
                    </RaisedButton>
                    <RaisedButton label="Bearbeiten" onClick={this.handleSeminarUpdate}/>
                </div>
                }
                {
                    <div className="output-properties">
                        { Object.keys(SeminarTexts).map(this.renderProperties)}
                        <div className="property seminar-property target-level">
                            <label>Zielgruppe(n)</label>
                            {this.state.seminar.targetLevel && this.state.seminar.targetLevel.map(this.renderTargetLevels)}
                        </div>
                        <div className="property seminar-property costs-per-participant">
                            <label>Kosten pro Teilnehmer</label>
                            {this.state.seminar.costsPerParticipant / 100 /*divide with 100 because the backend provides in cent*/}
                            €
                        </div>
                        <div className="property seminar-property dates">
                            <label>Termin(e)</label>
                            {this.state.seminar.dates && this.state.seminar.dates.map(this.renderDates)}
                        </div>
                        <div className="property seminar-property participants">
                            <label>Buchungen</label>
                            {this.state.seminar.activeBookings} von {this.state.seminar.maximumParticipants}
                        </div>
                    </div>
                }
                <div className="button-wrapper">
                    { AuthService.isFrontOffice() &&
                    <div>
                        <Link to="/invoice" target="_blank">
                            <RaisedButton label="Rechnung generieren"/>
                        </Link>
                    </div>
                    }
                    <RaisedButton label="Buchen" onClick={this.openBookingDialog} primary={true}
                                  disabled={this.checkIfCurrentUserHasAlreadyBooked() || this.maximumParticipantsAchieved() || !this.isBookable()}>
                        <Dialog actions={bookingActions} modal={false} open={this.state.bookingAlertOpen}
                                onRequestClose={this.close}>
                            <div>Jetzt das Seminar <span className="highlight-text">"{this.state.seminar.name}" </span>
                                buchen?
                            </div>
                        </Dialog>
                    </RaisedButton>
                </div>
            </div>
        );
    }
}

