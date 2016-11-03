import {SeminarList} from './components/seminar/SeminarList';
import React from 'react';
import ReactDOM from 'react-dom';
import 'whatwg-fetch'; //Github fetch
import {loginUser, seminarService} from "./services/LoginService";

require("./App.scss");
var url = require("./assets/aline_500x500.png");

class App extends React.Component {
    constructor() {
        super();
        this.state = {hello: 'Hello, I am Aline.'};
        loginUser('admin', 'admin');
        window.setTimeout(seminarService, 5000);
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
