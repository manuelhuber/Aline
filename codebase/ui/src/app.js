import {SeminarList} from './components/overview/SeminarList';
import {SeminarDetail} from './components/detail/SeminarDetail';
import {NotFound} from './components/NotFound';
import React from 'react';
import ReactDOM from 'react-dom';
import 'whatwg-fetch'; //Github fetch
import {Router, Route, Link, IndexRoute} from 'react-router'
import BrowserHistory from 'react-router/lib/BrowserHistory';
import {loginUser, seminarService} from "./services/LoginService";

require("./App.scss");
var url = require("./assets/aline_500x500.png");

class App extends React.Component {
    constructor() {
        super();
        this.state = {hello: 'Hello, I am Aline.'};
        //loginUser('admin', 'admin');
        //window.setTimeout(seminarService, 5000);
    }

    render() {
        return (
            <div>
                <h1>{this.state.hello}</h1>
                <img src={url} alt="Aline Logo" className="aline-logo"/>
                <header>
                    <div><Link to="/" activeClassName="active-nav">Seminarübersicht</Link></div>
                </header>
                <main>
                    {this.props.children}
                </main>
            </div>
        );
    }
}

ReactDOM.render(
    <Router history={BrowserHistory}>
        <Route path="/" component={App}>
            <IndexRoute component={SeminarList}/> {/* Default/At the beginning shown component */}
            <Route path="seminars/:seminarName" component={SeminarDetail}/>
            <Route path="*" component={NotFound}/>
        </Route>
    </Router>
    , document.getElementById('content'));
