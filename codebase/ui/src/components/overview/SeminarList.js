import React from 'react';
import {SeminarListItem} from './SeminarListItem';
import {SearchBar} from '../general/SearchBar';
import SeminarService from '../../services/SeminarService';

export class SeminarList extends React.Component {
    constructor() {
        super();
        this.renderSeminar = this.renderSeminar.bind(this);
        this.saveSeminars = this.saveSeminars.bind(this);
        this.filterSeminars = this.filterSeminars.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
        this.state = {
            seminars: [],
            filteredSeminars: [],
        }
    }

    componentDidMount() {
        let seminars = SeminarService.getAllSeminars();
        seminars.then(
            result => {
                this.saveSeminars(result)
            }
        );

    }

    saveSeminars(result) {
        this.setState({
            seminars: result,
            filteredSeminars: result
        })
    }

    filterSeminars(category) {
        let filteredSeminars = this.state.seminars.filter(seminar => seminar.category === category);
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    clearFilter(){
        this.setState({
            filteredSeminars : this.state.seminars
        })
    }

    render() {
        return (
            <div>
                <SearchBar searchBarType="overview" filterSeminars={this.filterSeminars} clearFilter={this.clearFilter}/>
                <main className="seminar-tiles">
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