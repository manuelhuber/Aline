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
        };
        this.handleTextSearch = this.handleTextSearch.bind(this);
        this.searchForText = this.searchForText.bind(this);
        this.chooseCategory = this.chooseCategory.bind(this);
        this.chooseTier = this.chooseTier.bind(this);
        this.showPastSeminars = this.showPastSeminars.bind(this);
        this.saveCategories = this.saveCategories.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
    }

    componentDidMount() {
        this.setState({
            isFrontOffice: AuthService.isFrontOffice()
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
        //todo search functionality
    }

    searchForText() {
        //todo
    }

    showPastSeminars() {
        //todo
    }

    chooseCategory(event, index, value) {
        this.setState({
            categoryDropdownValue: value,
        });
        this.props.filterSeminars(value);
    }

    chooseTier(event, index, value) {
        this.setState({
            tierDropdownValue: value
        })
    }

    renderCategories(category) {
        return (
            <MenuItem value={category} primaryText={category} id={category}/>
        )
    }

    clearFilter() {
        this.setState({
            categoryDropdownValue: '',
        });
        this.props.clearFilter();
    }

    render() {
        if (this.props.searchBarType == 'department') {
            return (
                <div className="search-bar">
                    Bereichssuchleiste
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
                            { this.state.categories.map(this.renderCategories) }
                        </SelectField>
                    </div>
                    <div className="dropdown">
                        <SelectField hintText="Filterstufe wählen"
                                     value={this.state.tierDropdownValue}
                                     onChange={this.chooseTier}>
                            <MenuItem value={1} primaryText="SE1"/>
                            <MenuItem value={2} primaryText="SE2"/>
                            <MenuItem value={3} primaryText="SE3"/>
                        </SelectField>
                    </div>
                    {this.state.isFrontOffice &&
                    <div className="checkbox-wrapper">
                        <Toggle label="In der Vergangenheit liegende Seminare anzeigen"
                                onToggle={this.showPastSeminars}/>
                    </div>
                    }
                    <FlatButton onClick={this.clearFilter} label="Filter löschen"
                                icon={<FontIcon className="material-icons">clear</FontIcon>}
                    />
                </div>
            );
        }
    }
}