import React from 'react';
import Util from '../../services/Util';
import SeminarService from '../../services/SeminarService';
import AuthService from '../../services/AuthService';
import Seminar from '../../models/Seminar';
import TextField from 'material-ui/TextField';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import IconButton from 'material-ui/IconButton';
import DatePicker from 'material-ui/DatePicker';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import Checkbox from 'material-ui/Checkbox';
import Dialog from 'material-ui/Dialog';
import Toggle from 'material-ui/Toggle';
import {PastSeminarList} from './PastSeminarList';

export class CreateSeminar extends React.Component {
    constructor() {
        super();
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        this.renderPickedDates = this.renderPickedDates.bind(this);
        this.renderTargetLevels = this.renderTargetLevels.bind(this);
        this.renderSelectMenuItems = this.renderSelectMenuItems.bind(this);

        this.openCopyDialog = this.openCopyDialog.bind(this);
        this.closeCopyDialog = this.closeCopyDialog.bind(this);
        this.chooseSeminarToCopy = this.chooseSeminarToCopy.bind(this);
        this.preFillSeminarData = this.preFillSeminarData.bind(this);

        this.toggleCreateAnotherSeminar = this.toggleCreateAnotherSeminar.bind(this);
        this.nameInput = this.nameInput.bind(this);
        this.agendaInput = this.agendaInput.bind(this);
        this.descriptionInput = this.descriptionInput.bind(this);
        this.datesInput = this.datesInput.bind(this);
        this.removeDate = this.removeDate.bind(this);
        this.contactPersonInput = this.contactPersonInput.bind(this);
        this.costsPerParticipantInput = this.costsPerParticipantInput.bind(this);
        this.cycleInput = this.cycleInput.bind(this);
        this.durationInput = this.durationInput.bind(this);
        this.goalInput = this.goalInput.bind(this);
        this.maximumParticipantsInput = this.maximumParticipantsInput.bind(this);
        this.requirementsInput = this.requirementsInput.bind(this);
        this.targetLevelInput = this.targetLevelInput.bind(this);
        this.removeTargetLevel = this.removeTargetLevel.bind(this);
        this.trainerInput = this.trainerInput.bind(this);
        this.trainingTypeInput = this.trainingTypeInput.bind(this);
        this.categoryInput = this.categoryInput.bind(this);
        this.bookingTimelogInput = this.bookingTimelogInput.bind(this);
        this.bookableInput = this.bookableInput.bind(this);
        this.state = {
            updatingExistingSeminar: false,
            seminarToCopyId: 0,
            isFrontOffice: false,
            createAnotherOne: false,
            availableCategories: [],
            availableTargetLevels: [],
            copyAlertOpen: false,

            nameMissingError: false,
            categoryMissingError: false,

            id: 0, //Use only when updating a seminar
            name: '',
            category: '',
            agenda: '',
            description: '',
            dates: [],
            contactPerson: '',
            costsPerParticipant: 0,
            cycle: '',
            duration: '',
            goal: '',
            maximumParticipants: 0,
            requirements: '',
            targetLevel: [],
            currentSelectedTargetLevel: '',
            trainer: '',
            trainingType: '',
            currentPickedDate: null,
            bookingTimelog: '',
            bookable: true
        }
    }

    componentDidMount() {
        let categories = SeminarService.getAllCategories();
        categories.then(
            result => {
                this.setState({
                    availableCategories: result
                })
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });

        this.setState({
            isFrontOffice: AuthService.isFrontOffice(),
            availableTargetLevels: SeminarService.getTargetLevels(),
            updatingExistingSeminar: (this.props.location.query && this.props.location.query.changeExisting) ? true: false
        });

        this.preFillSeminarData();
    }

    /**
     * Prefill the input fields with data, when a seminar id got handed by the
     * query 'changeExisting'
     * @param seminarId the id of the seminar to prefill the data with. May be null when used the query method
     */
    preFillSeminarData(seminarId) {
        if (seminarId || this.props.location.query && this.props.location.query.changeExisting) {
            var seminarToUpdate;
            if (seminarId) {
                seminarToUpdate = SeminarService.getSeminarById(seminarId);
            }
            else {
                seminarToUpdate = SeminarService.getSeminarById(this.props.location.query.changeExisting);
            }
            seminarToUpdate.then(
                result => {
                    let formattedCostsPerParticipants = Util.formatMoneyFromCent(result.costsPerParticipant);
                    this.setState({
                        id: result.id || 0,
                        name: result.name,
                        category: result.category || '',
                        agenda: result.agenda || '',
                        description: result.description || '',
                        dates: (seminarId ? [] : result.dates) || [],
                        contactPerson: result.contactPerson || '',
                        costsPerParticipant: formattedCostsPerParticipants || 0,
                        cycle: result.cycle || '',
                        duration: result.duration || '',
                        goal: result.goal || '',
                        maximumParticipants: result.maximumParticipants || 0,
                        requirements: result.requirements || '',
                        targetLevel: result.targetLevel || [],
                        trainer: result.trainer || '',
                        trainingType: result.trainingType || '',
                        bookingTimelog: result.bookingTimelog || '',
                        bookable: result.bookable || true
                    })
                })
                .catch(failureResult => {
                    this.props.router.replace('/error');
                });
        }
    }

    toggleCreateAnotherSeminar() {
        this.setState({
            createAnotherOne: !this.state.createAnotherOne
        })
    }

    nameInput(event) {
        this.setState({name: event.target.value})
    }

    agendaInput(event) {
        this.setState({agenda: event.target.value})
    }

    descriptionInput(event) {
        this.setState({description: event.target.value})
    }

    datesInput(event, date) {
        let newDates = this.state.dates;
        newDates.push(date);
        this.setState({
            dates: newDates,
            currentPickedDate: null
        })
    }

    removeDate(event) {
        let elementToRemoveIndex = event.currentTarget.name;

        let dates = this.state.dates.filter((currentValue, index) => {
            if (index != elementToRemoveIndex) {
                return currentValue;
            }
        });

        this.setState({
            dates: dates
        })
    }

    removeTargetLevel(event) {
        let elementToRemoveIndex = event.currentTarget.name;

        let targetLevel = this.state.targetLevel.filter((currentValue, index) => {
            if (index != elementToRemoveIndex) {
                return currentValue;
            }
        });

        this.setState({
            targetLevel: targetLevel
        })
    }

    contactPersonInput(event) {
        this.setState({contactPerson: event.target.value})
    }

    costsPerParticipantInput(event) {
        this.setState({costsPerParticipant: event.target.value})
    }

    cycleInput(event) {
        this.setState({cycle: event.target.value})
    }

    durationInput(event) {
        this.setState({duration: event.target.value})
    }

    goalInput(event) {
        this.setState({goal: event.target.value})
    }

    maximumParticipantsInput(event) {
        this.setState({maximumParticipants: event.target.value})
    }

    requirementsInput(event) {
        this.setState({requirements: event.target.value})
    }


    bookingTimelogInput(event) {
        this.setState({bookingTimelog: event.target.value})
    }

    targetLevelInput(event, index, value) {
        let targetLevel = this.state.targetLevel;
        targetLevel.push(value);
        this.setState({
            targetLevel: targetLevel,
            currentSelectedTargetLevel: ''
        });
    }

    categoryInput(event, index, value) {
        this.setState({
            category: value
        });
    }

    trainerInput(event) {
        this.setState({trainer: event.target.value})
    }

    trainingTypeInput(event) {
        this.setState({trainingType: event.target.value})
    }

    bookableInput() {
        let bookable = !(this.state.bookable);
        this.setState({bookable: bookable})
    }

    handleCancel() {
        this.props.router.replace('/seminars');
    }

    handleSubmit() {
        if (!this.state.name && !this.state.category) {
            this.setState({
                nameMissingError: true,
                categoryMissingError: true
            })
        }
        if (!this.state.name) {
            this.setState({
                nameMissingError: true
            })
        }
        if (!this.state.category) {
            this.setState({
                categoryMissingError: true
            })
        }
        else {
            let formattedCostsPerParticipant = Util.formatMoneyToCent(this.state.costsPerParticipant);
            var seminar = new Seminar(this.state.name, this.state.description, this.state.agenda, this.state.bookable, this.state.category, this.state.targetLevel,
                this.state.requirements, this.state.trainer, this.state.contactPerson, this.state.trainingType, this.state.maximumParticipants,
                formattedCostsPerParticipant, this.state.bookingTimelog, this.state.goal, this.state.duration, this.state.cycle, this.state.dates);
            //Make the call
            var response;
            if (this.state.updatingExistingSeminar) {
                response = SeminarService.updateSeminar(seminar, this.state.id);
            }
            else if (!this.state.updatingExistingSeminar) {
                response = SeminarService.addSeminar(seminar);
            }
            //Handle the response
            response.then(
                result => {
                    if (this.props.location.query.changeExisting) {
                        this.props.showSnackbar('Seminar erfolgreich bearbeitet');
                    }
                    else {
                        this.props.showSnackbar('Seminar erfolgreich erstellt');
                    }
                    if (this.state.createAnotherOne) {
                        window.setTimeout(function () {
                            location.reload();
                        }, 2000);
                    } else {
                        this.props.router.replace('/seminars/' + result.id);
                    }
                })
                .catch(failureResult => {
                    this.props.router.replace('/error');
                });
        }
    }

    /**
     * Copy data from old seminar
     */
    chooseSeminarToCopy(seminarId) {
        this.setState({
            seminarToCopyId: seminarId
        });
        this.preFillSeminarData(seminarId);
        this.closeCopyDialog();
    }

    openCopyDialog() {
        this.setState({
            copyAlertOpen: true
        })
    }

    closeCopyDialog() {
        this.setState({
            copyAlertOpen: false
        })
    }

    renderPickedDates(date, index) {
        return (
            <p key={index}>
                {new Date(date).toLocaleDateString()}
                <IconButton iconClassName="material-icons" name={index}
                            onClick={this.removeDate}>remove_circle_outline</IconButton>
            </p>
        )
    }

    renderTargetLevels(targetLevel, index) {
        return (
            <p key={index}>
                {targetLevel}
                <IconButton iconClassName="material-icons" name={index} key={index}
                            onClick={this.removeTargetLevel}>remove_circle_outline</IconButton>
            </p>
        )
    }

    renderSelectMenuItems(value) {
        return (
            <MenuItem value={value} primaryText={value} key={value}/>
        )
    }

    render() {
        const dialogStyle = {
            height: '500px'
        };

        const copyActions = [
            <FlatButton label="Abbrechen" onClick={this.closeCopyDialog}/>,
        ];
        return (
            <div className="create">
                {!this.state.updatingExistingSeminar && <h2>Neues Seminar erstellen:</h2>}
                {this.state.updatingExistingSeminar && <h2>Seminar bearbeiten:</h2>}
                {this.state.isFrontOffice &&
                <form className="create-seminar">
                    <div className="copy-button">
                        <RaisedButton label="Inhalte reinkopieren" onClick={this.openCopyDialog} secondary={true}>
                            <Dialog actions={copyActions} modal={false} open={this.state.copyAlertOpen}
                                    contentStyle={dialogStyle}
                                    onRequestClose={this.close}>
                                <h3>Kopiere die Daten aus einem vorherigen Seminar:</h3>
                                <p className="tipp-text">(Tipp: Positioniere den Mauszeiger über gekürzt dargestellten
                                    Texten, um den kompletten Text zu sehen)</p>
                                <PastSeminarList chooseSeminar={this.chooseSeminarToCopy}/>
                            </Dialog>
                        </RaisedButton>
                    </div>
                    <div>
                        <TextField onChange={this.nameInput} fullWidth={true}
                                   floatingLabelText="Name (Pflichtfeld)" floatingLabelFixed={true}
                                   value={this.state.name} id="name"
                                   errorText={this.state.nameMissingError === true && "Das Seminar braucht einen Namen."}/>
                    </div>
                    <div>
                        <SelectField floatingLabelText="Kategorie (Pflichtfeld)" floatingLabelFixed={true} fullWidth={true}
                                     value={this.state.category} onChange={this.categoryInput}
                                     errorText={this.state.categoryMissingError === true && "Bitte wähle eine Kategorie."}>
                            { this.state.availableCategories.map(this.renderSelectMenuItems) }
                        </SelectField>
                    </div>
                    <div>
                        <TextField onChange={this.descriptionInput} fullWidth={true}
                                   floatingLabelText="Beschreibung" floatingLabelFixed={true}
                                   value={this.state.description} id="description"
                                   multiLine={true} rows={2} rowsMax={4}/>
                    </div>
                    <div>
                        <TextField onChange={this.agendaInput} fullWidth={true}
                                   floatingLabelText="Agenda" floatingLabelFixed={true}
                                   value={this.state.agenda} id="agenda"
                                   multiLine={true} rows={2} rowsMax={4}/>
                    </div>
                    <div className="datepicker">
                        <DatePicker floatingLabelText="Termine" floatingLabelFixed={true}
                                    value={this.state.currentPickedDate} onChange={this.datesInput}/>
                        <div className="picked-dates">{this.state.dates.map(this.renderPickedDates)}</div>
                    </div>
                    <div>
                        <TextField onChange={this.contactPersonInput} fullWidth={true}
                                   floatingLabelText="Ansprechpartner bei Interesse" floatingLabelFixed={true}
                                   value={this.state.contactPerson} id="contactPerson"/>
                    </div>
                    <div>
                        <TextField onChange={this.costsPerParticipantInput} type="number" min="0" fullWidth={true}
                                   floatingLabelText="Kosten pro Teilnehmer (in €)" floatingLabelFixed={true}
                                   value={this.state.costsPerParticipant} id="costsPerParticipant"/>
                    </div>
                    <div>
                        <TextField onChange={this.cycleInput} fullWidth={true}
                                   floatingLabelText="Turnus" floatingLabelFixed={true}
                                   value={this.state.cycle} id="turnus"/>
                    </div>
                    <div>
                        <TextField onChange={this.durationInput} fullWidth={true}
                                   floatingLabelText="Dauer" floatingLabelFixed={true}
                                   value={this.state.duration} id="duration"/>
                    </div>
                    <div>
                        <TextField onChange={this.goalInput} fullWidth={true}
                                   floatingLabelText="Geplante Weiterentwicklungen" floatingLabelFixed={true}
                                   value={this.state.goal} id="goal"/>
                    </div>
                    <div>
                        <TextField onChange={this.maximumParticipantsInput} type="number" min="0" fullWidth={true}
                                   floatingLabelText="Maximale Teilnehmeranzahl" floatingLabelFixed={true}
                                   value={this.state.maximumParticipants} id="maximumParticipants"/>
                    </div>
                    <div>
                        <TextField onChange={this.requirementsInput} fullWidth={true}
                                   floatingLabelText="Voraussetzungen" floatingLabelFixed={true}
                                   value={this.state.requirements} id="requirements"/>
                    </div>
                    <div className="target-levels">
                        <SelectField floatingLabelText="Zielgruppe (in Stufen)" floatingLabelFixed={true}
                                     fullWidth={true}
                                     value={this.state.currentSelectedTargetLevel}
                                     onChange={this.targetLevelInput}>
                            { this.state.availableTargetLevels.map(this.renderSelectMenuItems) }
                        </SelectField>
                        <div
                            className="picked-target-levels">{this.state.targetLevel.map(this.renderTargetLevels)}</div>
                    </div>
                    <div>
                        <TextField onChange={this.trainerInput} fullWidth={true}
                                   floatingLabelText="Referent/externer Anbieter" floatingLabelFixed={true}
                                   value={this.state.trainer} id="trainer"/>
                    </div>
                    <div>
                        <TextField onChange={this.trainingTypeInput} fullWidth={true}
                                   floatingLabelText="Schulungsformat" floatingLabelFixed={true}
                                   value={this.state.trainingType} id="type"/>
                    </div>
                    <div>
                        <TextField onChange={this.bookingTimelogInput} fullWidth={true}
                                   floatingLabelText="Kontierung (im Timelog)" floatingLabelFixed={true}
                                   value={this.state.bookingTimelog} id="type"/>
                    </div>
                    <div></div>
                    <div className="toggle-element">
                        <Toggle label="Seminar ist buchbar" labelPosition="right" value={this.state.bookable}
                                defaultToggled={true}
                                onToggle={this.bookableInput}/>
                    </div>
                    <div className="action-elements">
                        <div className="checkbox">
                            <Checkbox label="Ein weiteres Seminar anlegen" value={this.state.createAnotherOne}
                                      onCheck={this.toggleCreateAnotherSeminar}/>
                        </div>
                        <div className="button-wrapper">
                            <RaisedButton label="Abbrechen" onClick={this.handleCancel} id="cancelButton"/>
                            <RaisedButton label="Speichern" onClick={this.handleSubmit} id="loginButton"
                                          primary={true}/>
                        </div>
                    </div>
                </form>
                }
                {!this.state.isFrontOffice &&
                <div>Entschuldige. Dieser Bereich ist nur mit entsprechenden Berechtigungen zugänglich. Bitte wende dich
                    an die Admins, wenn du denkst hier liegt ein Fehler vor.</div>}
            </div>
        );
    }
}