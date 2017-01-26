/**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';
import {Link, hashHistory} from 'react-router'
import AuthService from '../../services/AuthService';
import StorageService from '../../services/StorageService';
import {Popover} from 'material-ui/Popover';
import Menu from 'material-ui/Menu';
import MenuItem from 'material-ui/MenuItem';
import IconButton from 'material-ui/IconButton';

export class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            profileOpen: false
        };
        this.openMenu = this.openMenu.bind(this);
        this.closeMenu = this.closeMenu.bind(this);
        this.navigateToProfile = this.navigateToProfile.bind(this);
        this.logOut = this.logOut.bind(this);
    };

    componentDidMount() {
        this.setState({
            userName: StorageService.getUserFullName()
        })
    }

    openMenu(event) {
        this.setState({
            profileOpen: true,
            anchorEl: event.currentTarget
        });
    }

    closeMenu() {
        this.setState({
            profileOpen: false
        })
    }

    navigateToProfile() {
        hashHistory.push('/profile');
    }

    logOut() {
        AuthService.logoutUser();
        location.reload();
    }

    render() {
        return (
            <header className="header">
                <div className="navlink" title="Zur Seminarübersicht navigieren. Sehe hier alle verfügbaren Seminare ein und navigiere zu den Detailansichten der Seminare.">
                    <Link to="/seminars" activeClassName="active">
                        <i className="material-icons md-light">list</i>
                        Seminarübersicht
                    </Link>
                </div>
                {AuthService.isDivisionHead() &&
                <div className="navlink" title="Zur Bereichsübersicht navigieren. Hier sind Budgetinformationen zum Bereich einsehbar, und die Mitarbeiter des Bereichs.">
                    <Link to="/bereichuebersicht" activeClassName="active">
                        <i className="material-icons md-light">group</i>
                        Mein Bereich
                    </Link>
                </div>
                }
                <div className="profile">
                    <IconButton
                        iconClassName="material-icons md-light" tooltip="Profil und Logout" onClick={this.openMenu}>account_box</IconButton>
                    <Popover open={this.state.profileOpen} anchorEl={this.state.anchorEl}
                             anchorOrigin={{horizontal: 'middle', vertical: 'bottom'}}
                             targetOrigin={{horizontal: 'right', vertical: 'top'}}
                             onRequestClose={this.closeMenu}>
                        <Menu>
                            <MenuItem primaryText={this.state.userName} disabled={true} title="Das bist du!"/>
                            <MenuItem primaryText="Öffne Profil" onClick={this.navigateToProfile} title="Navigiere zu deinem eigenen Profil."/>
                            <MenuItem primaryText="Ausloggen" onClick={this.logOut}/>
                        </Menu>
                    </Popover>
                </div>
            </header>
        );
    }
}

