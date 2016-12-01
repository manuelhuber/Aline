import React from 'react';
import {Link} from 'react-router'
import {SeminarListItem} from './SeminarListItem';
import {SearchBar} from '../general/SearchBar';
import SeminarService from '../../services/SeminarService';
import AuthService from '../../services/AuthService';

export class SeminarList extends React.Component {
    constructor() {
        super();
        this.renderSeminar = this.renderSeminar.bind(this);
        this.saveSeminars = this.saveSeminars.bind(this);
        this.searchForText = this.searchForText.bind(this);
        this.filterSeminars = this.filterSeminars.bind(this);
        this.filterBoth = this.filterBoth.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
        this.state = {
            seminars: [],
            filteredSeminars: [],
            isFrontOffice: false,
        }
    }

    componentDidMount() {
        let seminars = SeminarService.getAllSeminars();
        seminars.then(
            result => {
                this.saveSeminars(result)
            }
        );
        this.setState({
            isFrontOffice: AuthService.isFrontOffice()
        });
    }

    saveSeminars(result) {
        this.setState({
            seminars: result,
            filteredSeminars: result
        })
    }

    searchForText(textToSearchFor) {
        let filteredSeminars = this.state.seminars.filter(seminar => JSON.stringify(seminar).toLowerCase().includes(textToSearchFor.toLowerCase()));
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    filterSeminars(category, targetLevel) {
        let filteredSeminars = [];
        //Category yes && targetLevel no
        if (category && !targetLevel) {
            filteredSeminars = this.state.seminars.filter(seminar => seminar.category === category);
        }
        //Category no && targetLevel yes
        else if (!category && targetLevel) {
            filteredSeminars = this.state.seminars.filter(seminar => seminar.targetLevel[0] === targetLevel);
        }
        //Category yes && targetLevel yes
        else if (category && targetLevel) {
            filteredSeminars = this.state.seminars.filter(seminar => seminar.category === category);
            filteredSeminars = filteredSeminars.filter(seminar => seminar.targetLevel[0] === targetLevel);
        }
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    filterBoth() {

    }

    clearFilter() {
        this.setState({
            filteredSeminars: this.state.seminars
        })
    }

    render() {
        return (
            <div>
                <SearchBar searchBarType="overview" filterSeminars={this.filterSeminars}
                           clearFilter={this.clearFilter} searchForText={this.searchForText}/>
                <main className="seminar-tiles">
                    {this.state.isFrontOffice &&
                    <div className="add-seminar-tile">
                        <Link to={`create`}>
                            <i className="material-icons">add</i>
                        </Link>
                    </div>
                    }
                    { this.state.filteredSeminars.map(this.renderSeminar) }
                    { this.state.filteredSeminars.length < 1 &&
                    <div className="no-seminar-found">
                        <i className="material-icons md-36">sentiment_neutral</i>
                        <p title="Ja, schlechte Sprüche sind cool!">Ein Satz mit X das war wohl Nix.</p>
                    </div>}
                </main>
            </div>
        );
    }

    renderSeminar(currentSeminar) {
        return <SeminarListItem seminar={currentSeminar}
                                key={currentSeminar.id}/>;
    }

}