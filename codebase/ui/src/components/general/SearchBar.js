import React from 'react';
import AuthService from '../../services/AuthService';
require('../../assets/dropdown_arrow.svg');

var DEFAULT_DROPDOWN_VALUE = 'Bitte wählen...';

export class SearchBar extends React.Component {
    constructor() {
        super();
        this.state = {
            textSearchInput: '',
            isFrontOffice: false,
            dropdownValue : ''
        };
        this.handleTextSearch = this.handleTextSearch.bind(this);
        this.searchForText = this.searchForText.bind(this);
        this.chooseDropdownValue = this.chooseDropdownValue.bind(this);
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

    chooseDropdownValue(event){
        this.setState({
            dropdownValue: event.target.value
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
                        <select value={this.state.dropdownValue} onChange={this.chooseDropdownValue}>
                            <option disabled>{DEFAULT_DROPDOWN_VALUE}</option>
                            <option>Blubb</option>
                            <option>Blah</option>
                            <option>42</option>
                            <option>foo</option>
                        </select>
                    </div>
                    {this.state.isFrontOffice &&
                    <div className="checkbox-wrapper">
                        <input type="checkbox" value="true" id="showOldSeminarsCheckbox"/>
                        <label htmlFor="showOldSeminarsCheckbox">Nur in der Vergangenheit liegende Seminare anzeigen</label>
                    </div>
                    }
                </div>
            );
        }
    }
}