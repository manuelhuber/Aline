import {SeminarList} from './seminar/SeminarList';
import React from 'react';
import ReactDOM from 'react-dom';

require("./App.scss");
var url = require("./assets/aline_500x500.png");

class App extends React.Component {
    constructor() {
        super();
        this.state = {hello: 'Hello, I am Aline.'}
    }

    render() {
        return (
            <div>
                <h1>{this.state.hello}</h1>
                <img src={url} alt="Aline Logo" className="aline-logo" />
                <SeminarList />
            </div>
        );
    }
}

ReactDOM.render(<App />, document.getElementById('content'));
