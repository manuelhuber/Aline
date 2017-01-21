import React from 'react';
import LoginService from '../../services/AuthService';
import TextField from 'material-ui/TextField';
//Material ui
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import FlatButton from 'material-ui/FlatButton';

export class Login extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.nameInput = this.nameInput.bind(this);
        this.passwordInput = this.passwordInput.bind(this);
        this.onEnterSubmit = this.onEnterSubmit.bind(this);
        this.state = {
            error: false,
            loginName: '',
            loginPassword: ''
        }
    }

    nameInput(event) {
        this.setState({
            loginName: event.target.value
        })
    }

    passwordInput(event) {
        this.setState({
            loginPassword: event.target.value
        })
    }

    handleSubmit() {
        LoginService.loginUser(this.state.loginName, this.state.loginPassword)
            .then(() => {
            if (LoginService.isLoggedIn()) {
                this.props.router.replace('/')
            }
            else {
                this.setState({
                    error: true
                })
            }
        });
    }

    onEnterSubmit(event) {
        if (event.key === 'Enter')
            this.handleSubmit();
    }

    render() {
        return (
            <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                <div className="login-form">
                    <h1>Aline</h1>
                    <img src={require("../../assets/aline_500x500.png")} alt="Aline Logo"/>
                    <div className="input-wrapper">
                        <div>
                            <TextField onChange={this.nameInput}
                                       floatingLabelText="Name" floatingLabelFixed={true}
                                       value={this.state.loginName} id="loginName"
                                       errorText={this.state.error === true && "Daten nicht korrekt"}/>
                        </div>
                        <div>
                            <TextField onChange={this.passwordInput}
                                       floatingLabelText="Passwort" floatingLabelFixed={true} onKeyDown={this.onEnterSubmit}
                                       value={this.state.passwordInput} type="password" id="loginPassword"
                                       errorText={this.state.error === true && "Daten nicht korrekt"}/>
                        </div>
                    </div>
                    <div className="button-wrapper">
                        <FlatButton label="Einloggen" onClick={this.handleSubmit} id="loginButton"/>
                    </div>
                </div>
            </MuiThemeProvider>
        )
    }
}