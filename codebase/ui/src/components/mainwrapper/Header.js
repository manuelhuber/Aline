    /**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';
import {Link, hashHistory} from 'react-router'
import AuthService from '../../services/AuthService';
import StorageService from '../../services/StorageService';

export class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isDivisionHead: AuthService.isDivisionHead(),
            userName: ''
        };
        this.toggleVisibility = this.toggleVisibility.bind(this);
        this.navigateToProfile = this.navigateToProfile.bind(this);
        this.logOut = this.logOut.bind(this);
    };

    componentDidMount(){
        this.setState({
            userName: StorageService.getUserFullName()
        })
    }

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
        AuthService.logoutUser();
        location.reload();
    }

    render() {
        return (
            <header className="header">
                <div className="navlink">
                    <Link to="/seminars" activeClassName="active">
                        <i className="material-icons md-light">list</i>
                        Seminarübersicht
                    </Link>
                </div>
                {this.state.isDivisionHead &&
                <div className="navlink">
                    <Link to="/bereichuebersicht" activeClassName="active">
                        <i className="material-icons md-light">group</i>
                        Mein Bereich
                    </Link>
                </div>
                }
                <div className="profile">
                    <div onClick={this.toggleVisibility}>
                        <i className="material-icons md-light" title="Profil und Logout">account_box</i>
                    </div>
                    <div id="profileDropdown" className="dropdown invisible">
                        <p className="user-name">{this.state.userName}</p>
                        <p className="open-profile" onClick={this.navigateToProfile}>Öffne Profil</p>
                        <p className="log-out" onClick={this.logOut}>Ausloggen</p>
                    </div>
                </div>
            </header>
        );
    }
}

