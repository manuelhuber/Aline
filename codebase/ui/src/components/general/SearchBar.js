import React from 'react';
require('../../assets/dropdown_arrow.svg');

export class SearchBar extends React.Component {
    constructor() {
        super();
        this.state = {
            textSearchInput: ''
        };
        this.handleTextSearch = this.handleTextSearch.bind(this);
        this.searchForText = this.searchForText.bind(this);
    }

    handleTextSearch(event) {
        this.setState(
            {textSearchInput: event.target.value}
        );
        //todo search functionality
    }

    searchForText() {

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
                        <button className="searchbutton" onClick={this.searchForText}><i className="material-icons">search</i></button>
                    </div>
                    <div className="dropdown">
                        <select>
                            <option value="" disabled selected className="placeholder-option">Kategorie wählen...</option>
                            <option value="blubb">Blubb</option>
                            <option value="Blah">Blah</option>
                            <option value="42">42</option>
                            <option value="foo">foo</option>
                        </select>
                    </div>
                </div>
            );
        }
    }
}