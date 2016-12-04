import React from 'react';
import SeminarService from '../../services/SeminarService';
import BookingService from '../../services/BookingService';
import StorageService from '../../services/StorageService';
import AuthService from '../../services/AuthService';
import RaisedButton from 'material-ui/RaisedButton';
import FlatButton from 'material-ui/FlatButton';
import Dialog from 'material-ui/Dialog';

//todo einzeln: Name, Teilnehmer (1 von this....maximaleTeilnehmeranzahl zB)
//Todo buchen button disabled wenn maximale teilnehmeranzahl erreicht
var SeminarTexts = {
    description: 'Beschreibung',
    agenda: 'Agenda',
    category: 'Kategorie',
    targetLevel: 'Zielgruppe',
    requirements: 'Voraussetzungen',
    trainer: 'Referent/externer Anbieter',
    contactPerson: 'Ansprechpartner bei Interesse',
    trainingType: 'Schulungsformat',
    maximumParticipants: 'Maximale Teilnehmeranzahl',
    costsPerParticipant: 'Kosten pro Teilnehmer (in Euro)',
    goal: 'Geplante Weiterentwicklungen',
    duration: 'Dauer',
    cycle: 'Turnus',
};

export class SeminarDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seminar: {},
            bookingAlertOpen: false,
            cancelAlertOpen: false,
            deleteAlertOpen: false,
        };
        this.setSeminar = this.setSeminar.bind(this);
        this.handleSeminarUpdate = this.handleSeminarUpdate.bind(this);
        this.renderProperties = this.renderProperties.bind(this);
        this.renderDates = this.renderDates.bind(this);
        this.renderBookings = this.renderBookings.bind(this);
        this.checkIfCurrentUserHasAlreadyBooked = this.checkIfCurrentUserHasAlreadyBooked.bind(this);
        //Booking
        this.handleBooking = this.handleBooking.bind(this);
        this.openBookingDialog = this.openBookingDialog.bind(this);
        this.closeBookingDialog = this.closeBookingDialog.bind(this);
        //Cancel
        this.handleCancel = this.handleCancel.bind(this);
        this.openCancelDialog = this.openCancelDialog.bind(this);
        this.closeCancelDialog = this.closeCancelDialog.bind(this);
        //Delete
        this.handleDelete = this.handleDelete.bind(this);
        this.openDeleteDialog = this.openDeleteDialog.bind(this);
        this.closeDeleteDialog = this.closeDeleteDialog.bind(this);
    }

    componentDidMount() {
        let seminar = SeminarService.getSeminarById(this.props.params.seminarName);
        seminar.then(
            result => {
                this.setSeminar(result)
            }
        );
    }

    checkIfCurrentUserHasAlreadyBooked() {
        if (this.state.seminar.bookings) {
            return this.state.seminar.bookings.includes(StorageService.getCurrentUser().user);
        }
        return false;
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
        BookingService.bookSeminar(this.state.seminar.id);
        this.closeBookingDialog();
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
     * Cancel
     */
    handleCancel() {
        //todo
        this.closeCancelDialog();
    }

    openCancelDialog() {
        this.setState({
            cancelAlertOpen: true
        })
    }

    closeCancelDialog() {
        this.setState({
            cancelAlertOpen: false
        })
    }

    /**
     * Delete
     */
    handleDelete() {
        SeminarService.deleteSeminar(this.state.seminar.id);
        this.closeDeleteDialog();
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
        //todo umleiten
    }

    /**
     * Create invoice
     */
    createInvoice() {
        //todo in neuem tab öffnen
    }

    renderProperties(key) {
        return (
            <div className="seminar-property">
                <output>
                    <label>{SeminarTexts[key]}</label>
                    {this.state.seminar[key]}
                </output>
            </div>
        )
    }

    renderDates(date) {
        return (
            <p>
                {new Date(date).toLocaleDateString()}
            </p>
        )
    }

    renderBookings(booking) {
        return (
            <p>
                {booking.username}
            </p>
        )
    }

    render() {
        const bookingActions = [
            <FlatButton label="Abbrechen" onClick={this.closeBookingDialog}/>,
            <FlatButton label="Buchen" primary={true} onClick={this.handleBooking}/>
        ];
        const cancelActions = [
            <FlatButton label="Abbrechen" onClick={this.closeCancelDialog}/>,
            <FlatButton label="Stornieren" primary={true} onClick={this.handleCancel}/>
        ];
        const deleteActions = [
            <FlatButton label="Abbrechen" onClick={this.closeDeleteDialog}/>,
            <FlatButton label="Löschen" primary={true} onClick={this.handleDelete}/>
        ];

        return (
            <div className="seminar">
                <h2>{this.state.seminar.name}</h2>
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
                    <div className="properties">
                        { Object.keys(SeminarTexts).map(this.renderProperties)}
                        <div className="seminar-property dates">
                            <label>Termine</label>
                            {this.state.seminar.dates && this.state.seminar.dates.map(this.renderDates)}
                        </div>
                        <div className="seminar-property participants">
                            {this.state.seminar.bookings &&
                            <label>Buchungen
                                ({this.state.seminar.bookings.length}/{this.state.seminar.maximumParticipants})</label>
                            }
                            {this.state.seminar.bookings && this.state.seminar.bookings.map(this.renderBookings)}
                        </div>
                    </div>
                }
                <div className="button-wrapper">
                    { AuthService.isFrontOffice() &&
                    <div>
                        <RaisedButton label="Rechnung generieren" onClick={this.createInvoice}/>
                        <RaisedButton label="Stornieren" onClick={this.openCancelDialog} secondary={true}>
                            <Dialog actions={cancelActions} modal={false} open={this.state.cancelAlertOpen}
                                    onRequestClose={this.close}>
                                Möchtest du das Seminar <span
                                className="highlight-text">"{this.state.seminar.name}" </span>
                                wirklich stornieren?
                            </Dialog>
                        </RaisedButton>
                    </div>
                    }
                    <RaisedButton label="Buchen" onClick={this.openBookingDialog} primary={true}
                                  disabled={this.checkIfCurrentUserHasAlreadyBooked()}>
                        <Dialog actions={bookingActions} modal={false} open={this.state.bookingAlertOpen}
                                onRequestClose={this.close}>
                            Jetzt das Seminar <span className="highlight-text">"{this.state.seminar.name}" </span>
                            buchen?
                        </Dialog>
                    </RaisedButton>
                </div>
            </div>
        );
    }
}

