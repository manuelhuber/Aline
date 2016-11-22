import {MainWrapper} from './components/mainwrapper/MainWrapper';
import {SeminarList} from './components/overview/SeminarList';
import {SeminarDetail} from './components/detail/SeminarDetail';
import {NotFound} from './components/general/NotFound';
import {Login} from './components/general/Login';
import React from 'react';
import ReactDOM from 'react-dom';
import 'whatwg-fetch'; //Github fetch
import {Router, Route, Link, IndexRedirect, hashHistory} from 'react-router'
import LoginService from './services/AuthService';

require('./App.scss');

class App extends React.Component {
    constructor() {
        super();
    }
}

function redirectToLogin(nextState, replace) {
    if (!LoginService.isLoggedIn()) {
        replace({
            pathname: '/login',
            state: {nextPathname: nextState.location.pathname}
        });
    }
}

function redirectToLogin(nextState, replace) {
    console.log('LoginService: ' + LoginService.isLoggedIn());
    if (!LoginService.isLoggedIn()) {
        replace({
            pathname: '/login',
            state: {nextPathname: nextState.location.pathname}
        });
    }
}

ReactDOM.render(
    <Router history={hashHistory}>
        <Route path="/login" component={Login}/>
        <Route path="/" component={MainWrapper} onEnter={redirectToLogin}>
            <IndexRedirect to="/seminars"/>
            <Route path="seminars" component={SeminarList}/>
            <Route path="seminars/:seminarName" component={SeminarDetail}/>
            <Route path="*" component={NotFound}/>
        </Route>
    </Router>
    , document.getElementById('content'));
