import React from 'react';
import {SeminarListItem} from './SeminarListItem';

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
            seminars: ['Lesen', 'Javascript', 'Agiles Arbeiten', 'Foo', 'Bar']
        })
    }

    render() {
        return (
            <div>
                <h2>Mögliche Seminare:</h2>
                <main>
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