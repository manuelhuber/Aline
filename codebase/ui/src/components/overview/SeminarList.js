import React from 'react';
import {SeminarListItem} from './SeminarListItem';
import {SearchBar} from '../general/SearchBar';
import SeminarService from '../../services/SeminarService';

export class SeminarList extends React.Component {
    constructor() {
        super();
        this.renderSeminar = this.renderSeminar.bind(this);
        this.saveSeminars = this.saveSeminars.bind(this);
        this.state = {
            seminars: []
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

    saveSeminars(result){
        this.setState({
            seminars: result
        })
    }

    render() {
        return (
            <div>
                <SearchBar/>
                <main className="seminar-tiles">
                    { this.state.seminars.map(this.renderSeminar) }
                </main>
            </div>
        );
    }

    renderSeminar(currentSeminar) {
        return <SeminarListItem seminar={currentSeminar}
                                key={currentSeminar.id}/>;
    }
}