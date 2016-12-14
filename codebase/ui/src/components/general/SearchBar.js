import React from 'react';
import AuthService from '../../services/AuthService';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';
import Toggle from 'material-ui/Toggle';
import TextField from 'material-ui/TextField';
import IconButton from 'material-ui/IconButton';
import FlatButton from 'material-ui/FlatButton';
import FontIcon from 'material-ui/FontIcon';
import SeminarService from '../../services/SeminarService';

export class SearchBar extends React.Component {
    constructor() {
        super();
        this.state = {
            textSearchInput: '',
            isFrontOffice: false,
            categoryDropdownValue: '',
            tierDropdownValue: '',
            categories: [],
            availableTiers: [],
            showPastSeminars: false
        };
        this.handleTextSearch = this.handleTextSearch.bind(this);
        this.searchForText = this.searchForText.bind(this);
        this.chooseCategory = this.chooseCategory.bind(this);
        this.chooseTier = this.chooseTier.bind(this);
        this.handlePastSeminarsToggle = this.handlePastSeminarsToggle.bind(this);
        this.saveCategories = this.saveCategories.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
    }

    componentDidMount() {
        this.setState({
            isFrontOffice: AuthService.isFrontOffice(),
            availableTiers: SeminarService.getTargetLevels()
        });

        let categories = SeminarService.getAllCategories();
        categories.then(
            result => {
                this.saveCategories(result)
            }
        );
    }

    saveCategories(result) {
        this.setState({
            categories: result
        })
    }

    handleTextSearch(event) {
        this.setState(
            {textSearchInput: event.target.value}
        );
    }

    searchForText() {
        this.props.searchForText(this.state.textSearchInput);
    }

    handlePastSeminarsToggle() {
        let showPastSeminars = !(this.state.showPastSeminars);
        this.setState({
            showPastSeminars: showPastSeminars
        });
        this.props.showPastSeminars(showPastSeminars); //Call the handed method
    }

    chooseCategory(event, index, value) {
        this.setState({
            categoryDropdownValue: value,
        });

        this.props.filterSeminars(value, this.state.tierDropdownValue);
    }

    chooseTier(event, index, value) {
        this.setState({
            tierDropdownValue: value
        });

        this.props.filterSeminars(this.state.categoryDropdownValue, value);
    }

    renderSelectMenuItems(value) {
        return (
            <MenuItem value={value} primaryText={value} id={value}/>
        )
    }

    clearFilter() {
        this.setState({
            categoryDropdownValue: '',
            tierDropdownValue: '',
            textSearchInput: ''
        });
        this.props.clearFilter();
    }

    render() {
        if (this.props.searchBarType == 'department') {
            return (
                <div className="search-bar">
                    <div className="text-search">
                        <TextField type="search" hintText="Mitarbeiter suchen" value={this.state.textSearchInput}
                                   onChange={this.handleTextSearch}/>
                        <IconButton onClick={this.searchForText} iconClassName="material-icons">search</IconButton>
                    </div>
                </div>
            )
        } else {
            return (
                <div className="search-bar">
                    <div className="text-search">
                        <TextField type="search" hintText="Volltextsuche" value={this.state.textSearchInput}
                                   onChange={this.handleTextSearch}/>
                        <IconButton onClick={this.searchForText} iconClassName="material-icons">search</IconButton>
                    </div>
                    <div className="dropdown">
                        <SelectField hintText="Filterkategorie wählen"
                                     value={this.state.categoryDropdownValue}
                                     onChange={this.chooseCategory}>
                            { this.state.categories.map(this.renderSelectMenuItems) }
                        </SelectField>
                    </div>
                    <div className="dropdown">
                        <SelectField hintText="Filterstufe wählen"
                                     value={this.state.tierDropdownValue}
                                     onChange={this.chooseTier}>
                            { this.state.availableTiers.map(this.renderSelectMenuItems) }
                        </SelectField>
                    </div>
                    <FlatButton onClick={this.clearFilter} label="Filter löschen"
                                icon={<FontIcon className="material-icons">clear</FontIcon>}
                    />
                    {this.state.isFrontOffice &&
                    <div className="checkbox-wrapper">
                        <Toggle label="In der Vergangenheit liegende Seminare anzeigen"
                                value={this.state.showPastSeminars}
                                onToggle={this.handlePastSeminarsToggle} labelPosition="right"/>
                    </div>
                    }
                </div>
            );
        }
    }
}