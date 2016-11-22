import React from 'react';
import {SeminarListItem} from './SeminarListItem';
import {SearchBar} from '../general/SearchBar';
import SeminarService from '../../services/SeminarService';

export class SeminarList extends React.Component {
    constructor() {
        super();
        this.renderSeminar = this.renderSeminar.bind(this);
        this.state = {
            seminars: []
        }
    }

    componentDidMount() {
        this.setState({
            //seminars: SeminarService.getAllSeminars()
            seminars: ['Lesen', 'Javascript', 'Agiles Arbeiten', 'Foo', 'Bar', 'Bliblablubb Scharupp Didupp', 'Laber laber laber', 'So ein tolles Seminar, buche mich', 'Stuff']
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
                                key={currentSeminar}/>;
    }
}