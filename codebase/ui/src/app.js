import {SeminarList} from './seminar/SeminarList';
import React from 'react';
import ReactDOM from 'react-dom';

require("!style!css!sass!./App.scss");

class App extends React.Component {
    constructor() {
        super();
        this.state = {hello: 'Hello, I am Aline :)'}
    }

    render() {
        return (
            <div>
                <h1>{this.state.hello}</h1>
                <SeminarList />
            </div>
        );
    }
}

ReactDOM.render(<App />, document.getElementById('content'));
