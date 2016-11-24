import React from 'react';
import LoginService from '../../services/AuthService';

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
                <h1>Aline</h1>
                <img src={require("../../assets/aline_500x500.png")} alt="Aline Logo"/>
                <div className="input-wrapper">
                    <div>
                        <label htmlFor="loginName">Login Name:</label>
                        <input type="text" ref="loginName" placeholder="Login Name" id="loginName"/>
                    </div>
                    <div>
                        <label htmlFor="password">Passwort:</label>
                        <input ref="password" placeholder="Passwort" type="password" id="password"/>
                    </div>
                </div>
                <div className="button-wrapper">
                    <button type="submit">Einloggen</button>
                </div>
                <div id="loginError" className="login-error invisible">
                    Daten nicht korrekt.
                </div>
            </form>
        )
    }
}