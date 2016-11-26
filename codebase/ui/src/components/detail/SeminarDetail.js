import React from 'react';
import SeminarService from '../../services/SeminarService';
import RaisedButton from 'material-ui/RaisedButton';
import FlatButton from 'material-ui/FlatButton';
import Dialog from 'material-ui/Dialog';

export class SeminarDetail extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            seminar: {},
            bookingAlertOpen: false
        };
        this.handleBooking = this.handleBooking.bind(this);
        this.saveSeminar = this.saveSeminar.bind(this);
        this.openBookingDialog = this.openBookingDialog.bind(this);
        this.closeBookingDialog = this.closeBookingDialog.bind(this);
    }

    componentDidMount() {
        let seminar = SeminarService.getSeminarById(this.props.params.seminarName);
        seminar.then(
            result => {
                this.saveSeminar(result)
            }
        );
    }

    saveSeminar(result) {
        this.setState({
            seminar: result
        })
    }

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

    render() {
        const bookingActions = [
            <FlatButton label="Abbrechen" onClick={this.closeBookingDialog}/>,
            <FlatButton label="Buchen" primary={true} onClick={this.handleBooking}/>
        ];

        return (
            <div className="seminar">
                <div className="seminar-name">
                    <label htmlFor="seminarName">Name: </label>
                    <output type="text" id="seminarName">{this.state.seminar.name}</output>
                </div>
                <div className="seminar-description">
                    <label htmlFor="seminarDescription">Beschreibung: </label>
                    <output type="text" id="seminarDescription">{this.state.seminar.description}</output>
                </div>
                <div className="seminar-trainer">
                    <label htmlFor="seminarTrainer">Typ: </label>
                    <output type="text" id="seminarTrainer">{this.state.seminar.trainer}</output>
                </div>
                <RaisedButton label="Buchen" onClick={this.openBookingDialog}>
                    <Dialog actions={bookingActions} modal={false} open={this.state.bookingAlertOpen}
                            onRequestClose={this.close}>
                        Jetzt das Seminar {this.state.seminar.name} buchen?
                    </Dialog>
                </RaisedButton>
            </div>
        );
    }
}

