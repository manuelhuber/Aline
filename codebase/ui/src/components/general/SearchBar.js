import React from 'react';
import AuthService from '../../services/AuthService';
require('../../assets/dropdown_arrow.svg');

var DEFAULT_DROPDOWN_VALUE = 'Bitte wähle';

export class SearchBar extends React.Component {
    constructor() {
        super();
        this.state = {
            textSearchInput: '',
            isFrontOffice: false,
            categoryDropdownValue: '',
            tierDropdownValue: ''
        };
        this.handleTextSearch = this.handleTextSearch.bind(this);
        this.searchForText = this.searchForText.bind(this);
        this.chooseCategory = this.chooseCategory.bind(this);
        this.chooseTier = this.chooseTier.bind(this);
    }

    componentDidMount() {
        this.setState({
            isFrontOffice: AuthService.isFrontOffice()
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

    chooseCategory(event) {
        this.setState({
            categoryDropdownValue: event.target.value
        })
    }

    chooseTier(event) {
        this.setState({
            tierDropdownValue: event.target.value
        })
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
                        <input type="search" placeholder="Volltextsuche" value={this.state.textSearchInput}
                               onChange={this.handleTextSearch}/>
                        <button className="searchbutton" onClick={this.searchForText}><i className="material-icons">search</i>
                        </button>
                    </div>
                    <div className="dropdown">
                        <select value={this.state.categoryDropdownValue} onChange={this.chooseCategory}>
                            <option disabled>{DEFAULT_DROPDOWN_VALUE} eine Kategorie zum Filtern</option>
                            <option>Blubb</option>
                            <option>Blah</option>
                            <option>42</option>
                            <option>foo</option>
                        </select>
                    </div>
                    <div className="dropdown">
                        <select value={this.state.tierDropdownValue} onChange={this.chooseTier}>
                            <option disabled>{DEFAULT_DROPDOWN_VALUE} eine Stufe zum Filtern</option>
                            <option>SE1</option>
                            <option>SE2</option>
                            <option>Tester</option>
                            <option>FO</option>
                        </select>
                    </div>
                    {this.state.isFrontOffice &&
                    <div className="checkbox-wrapper">
                        <input type="checkbox" value="true" id="showOldSeminarsCheckbox"/>
                        <label htmlFor="showOldSeminarsCheckbox">Nur in der Vergangenheit liegende Seminare
                            anzeigen</label>
                    </div>
                    }
                </div>
            );
        }
    }
}