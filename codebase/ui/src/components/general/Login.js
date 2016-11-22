import React from 'react';
import LoginService from '../../services/LoginService';

export class Login extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {
            error: false
        }
    }

    handleSubmit(event) {
        event.preventDefault();
        var email = this.refs.loginName.value;
        var pass = this.refs.password.value;

        LoginService.loginUser(email, pass).then(() => {
            if (LoginService.isLoggedIn()) {
                this.props.router.replace('/')
            }
            else {
                document.getElementById('loginError').classList.remove('invisible');
                this.forceUpdate();
            }
        });
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="login-form">
                <h2>Aline</h2>
                <div>
                    <label htmlFor="loginName">Login Name:</label>
                    <input ref="loginName" placeholder="Login Name" id="loginName"/>
                </div>
                <div>
                    <label htmlFor="password">Passwort:</label>
                    <input ref="password" placeholder="Passwort" type="password" id="password"/>
                </div>
                <button type="submit">Einloggen</button>
                <div id="loginError" className="login-error invisible">
                    Daten nicht korrekt.
                </div>
            </form>
        )
    }
}