import React from 'react';
import SeminarService from '../../services/SeminarService';
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
            cancelAlertOpen: false
        };
        this.setSeminar = this.setSeminar.bind(this);
        this.renderProperties = this.renderProperties.bind(this);
        this.renderDates = this.renderDates.bind(this);
        //Booking
        this.handleBooking = this.handleBooking.bind(this);
        this.openBookingDialog = this.openBookingDialog.bind(this);
        this.closeBookingDialog = this.closeBookingDialog.bind(this);
        //Cancel
        this.handleCancel = this.handleCancel.bind(this);
        this.openCancelDialog = this.openCancelDialog.bind(this);
        this.closeCancelDialog = this.closeCancelDialog.bind(this);
    }

    componentDidMount() {
        let seminar = SeminarService.getSeminarById(this.props.params.seminarName);
        seminar.then(
            result => {
                this.setSeminar(result)
            }
        );
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
        //todo
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

    render() {
        const bookingActions = [
            <FlatButton label="Abbrechen" onClick={this.closeBookingDialog}/>,
            <FlatButton label="Buchen" primary={true} onClick={this.handleBooking}/>
        ];
        const cancelActions = [
            <FlatButton label="Abbrechen" onClick={this.closeCancelDialog}/>,
            <FlatButton label="Stornieren" primary={true} onClick={this.handleCancel}/>
        ];
        return (
            <div className="seminar">
                <h2>{this.state.seminar.name}</h2>
                {
                    <div className="properties">
                        { Object.keys(SeminarTexts).map(this.renderProperties)}
                        <div className="seminar-property dates">
                            <label>Termine</label>
                            {this.state.seminar.dates && this.state.seminar.dates.map(this.renderDates)}
                        </div>
                        <div className="seminar-property participants">
                            todo bookings, teilnehmer blabla
                        </div>
                    </div>
                }
                <div className="button-wrapper">
                    { AuthService.isFrontOffice() &&
                    <RaisedButton label="Stornieren" onClick={this.openCancelDialog} secondary={true}>
                        <Dialog actions={cancelActions} modal={false} open={this.state.cancelAlertOpen}
                                onRequestClose={this.close}>
                            Möchtest du das Seminar <span className="highlight-text">"{this.state.seminar.name}" </span>
                            wirklich stornieren?
                        </Dialog>
                    </RaisedButton>
                    }
                    <RaisedButton label="Buchen" onClick={this.openBookingDialog} primary={true}>
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

