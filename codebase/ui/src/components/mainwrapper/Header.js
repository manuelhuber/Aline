/**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';
import {Link, hashHistory} from 'react-router'
import AuthService from '../../services/AuthService';

export class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isDivisionHead: AuthService.isDivisionHead()
        };
        this.toggleVisibility = this.toggleVisibility.bind(this);
        this.navigateToProfile = this.navigateToProfile.bind(this);
        this.logOut = this.logOut.bind(this);
    };

    toggleVisibility() {
        let classList = document.getElementById('profileDropdown').classList;
        if (classList.contains('invisible')) {
            classList.remove('invisible');
            this.forceUpdate();
        } else {
            classList.add('invisible');
            this.forceUpdate();
        }
    }

    navigateToProfile() {
        this.toggleVisibility();
        hashHistory.push('/profile');
    }

    logOut() {
        if (AuthService.logoutUser()) {
            hashHistory.push('/');
        }
        else {
            alert('Sry. Nicht geklappt.');
        }
    }

    render() {
        return (
            <header className="header">
                <div className="navlink">
                    <Link to="/seminars" activeClassName="active">
                        <i className="material-icons md-light md-36">list</i>
                        Seminarübersicht
                    </Link>
                </div>
                {this.state.isDivisionHead &&
                <div className="navlink">
                    <Link to="/bereichuebersicht" activeClassName="active">
                        <i className="material-icons md-light md-36">group</i>
                        Mein Bereich
                    </Link>
                </div>
                }
                <div className="profile">
                    <i className="material-icons md-light md-36" title="Profil und Logout"
                       onClick={this.toggleVisibility}>account_box</i>
                    <div id="profileDropdown" className="dropdown invisible">
                        <p className="user-name">Fritz</p>
                        <p className="open-profile" onClick={this.navigateToProfile}>Öffne Profil</p>
                        <p className="log-out" onClick={this.logOut}>Ausloggen</p>
                    </div>
                </div>
            </header>
        );
    }
}
