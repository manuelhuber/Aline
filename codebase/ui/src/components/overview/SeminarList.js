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
        this.filterTiers = this.filterTiers.bind(this);
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
        let filteredSeminars = this.state.seminars.filter( seminar => JSON.stringify(seminar).includes(textToSearchFor));
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    filterSeminars(category) {
        let filteredSeminars = this.state.seminars.filter(seminar => seminar.category === category);
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    filterTiers(targetLevel) {
        let filteredSeminars = this.state.seminars.filter(seminar => seminar.targetLevel === targetLevel);
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    clearFilter() {
        this.setState({
            filteredSeminars: this.state.seminars
        })
    }

    render() {
        return (
            <div>
                <SearchBar searchBarType="overview" filterSeminars={this.filterSeminars} filterTiers={this.filterTiers}
                           clearFilter={this.clearFilter} searchForText={this.searchForText}/>
                <main className="seminar-tiles">
                    {this.state.isFrontOffice &&
                    <div className="add-seminar-tile">
                        <Link to={`seminars/create`}>
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