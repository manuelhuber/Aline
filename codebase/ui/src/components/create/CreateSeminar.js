import React from 'react';
import SeminarService from '../../services/SeminarService';
import AuthService from '../../services/AuthService';
import Seminar from '../../models/Seminar';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import IconButton from 'material-ui/IconButton';
import DatePicker from 'material-ui/DatePicker';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import Checkbox from 'material-ui/Checkbox';

export class CreateSeminar extends React.Component {
    constructor() {
        super();
        this.handleCancel = this.handleCancel.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.renderPickedDates = this.renderPickedDates.bind(this);
        this.renderSelectMenuItems = this.renderSelectMenuItems.bind(this);

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
        this.trainerInput = this.trainerInput.bind(this);
        this.trainingTypeInput = this.trainingTypeInput.bind(this);
        this.categoryInput = this.categoryInput.bind(this);
        this.bookingTimelogInput = this.bookingTimelogInput.bind(this);
        this.state = {
            isFrontOffice: false,
            createAnotherOne: false,
            error: false,
            availableCategories: [],
            availableTargetLevels: [],

            name: '',
            category: '',
            agenda: '',
            description: '',
            dates: [], //date.getTime()
            contactPerson: '',
            costsPerParticipant: 0,
            cycle: '', //"Turnus"
            duration: '',
            goal: '',
            maximumParticipants: 0,
            requirements: '',
            targetLevel: 1,
            trainer: '',
            trainingType: '',
            currentPickedDate: null,
            bookingTimelog: ''
        }
    }

    componentDidMount() {
        let categories = SeminarService.getAllCategories();
        categories.then(
            result => {
                this.setState({
                    availableCategories: result
                })
            }
        );

        this.setState({
            isFrontOffice: AuthService.isFrontOffice(),
            availableTargetLevels: SeminarService.getTargetLevels()
        });
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
        this.setState({
            targetLevel: value
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

    handleCancel() {
        this.props.router.replace('/seminars');
    }

    handleSubmit() {
        var idOfTheNewSeminar = 0;
        if (!this.state.name) {
            this.setState({
                error: !this.state.name
            })
        } else {
            let targetLevel = [];
            targetLevel.push(this.state.targetLevel);
            var seminar = new Seminar(this.state.name, this.state.description, this.state.agenda, true, this.state.category, targetLevel,
                this.state.requirements, this.state.trainer, this.state.contactPerson, this.state.trainingType, this.state.maximumParticipants,
                this.state.costsPerParticipant, this.state.bookingTimelog, this.state.goal, this.state.duration, this.state.cycle, this.state.dates);
            //Get id out of response
            var response = SeminarService.addSeminar(seminar);
            response.then(
                result => {
                    if (this.state.createAnotherOne) {
                        location.reload();
                    } else {
                        this.props.router.replace('/seminars/' + result.id);
                    }
                },
                failureResult => {
                    this.props.router.replace('/error');
                }
            );
        }
    }

    renderPickedDates(date, index) {
        return (
            <p>
                {date.toLocaleDateString()}
                <IconButton iconClassName="material-icons" name={index}
                            onClick={this.removeDate}>remove_circle_outline</IconButton>
            </p>
        )
    }

    renderSelectMenuItems(value) {
        return (
            <MenuItem value={value} primaryText={value} id={value}/>
        )
    }

    render() {
        return (
            <div className="create">
                <h2>Neues Seminar erstellen: </h2>
                {this.state.isFrontOffice &&
                <form className="create-seminar">
                    <div>
                        <TextField onChange={this.nameInput} fullWidth={true}
                                   floatingLabelText="Name" floatingLabelFixed={true}
                                   value={this.state.name} id="name"
                                   errorText={this.state.error === true && "Das Seminar braucht einen Namen."}/>
                    </div>
                    <div>
                        <SelectField floatingLabelText="Kategorie wählen" floatingLabelFixed={true} fullWidth={true}
                                     value={this.state.category}
                                     onChange={this.categoryInput}>
                            { this.state.availableCategories.map(this.renderSelectMenuItems) }
                        </SelectField>
                    </div>
                    <div>
                        <TextField onChange={this.agendaInput} fullWidth={true}
                                   floatingLabelText="Agenda" floatingLabelFixed={true}
                                   value={this.state.agenda} id="agenda"
                                   multiLine={true} rows={2} rowsMax={4}/>
                    </div>
                    <div>
                        <TextField onChange={this.descriptionInput} fullWidth={true}
                                   floatingLabelText="Beschreibung" floatingLabelFixed={true}
                                   value={this.state.description} id="description"
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
                    <div>
                        <SelectField floatingLabelText="Zielgruppe wählen (in Stufen)" floatingLabelFixed={true}
                                     fullWidth={true}
                                     value={this.state.targetLevel}
                                     onChange={this.targetLevelInput}>
                            { this.state.availableTargetLevels.map(this.renderSelectMenuItems) }
                        </SelectField>
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
                        <TextField onChange={this.bookingTimelog} fullWidth={true}
                                   floatingLabelText="Kontierung (im Timelog)" floatingLabelFixed={true}
                                   value={this.state.bookingTimelog} id="type"/>
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