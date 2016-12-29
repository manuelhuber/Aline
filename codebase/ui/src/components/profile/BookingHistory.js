import React from 'react';
import {Link} from 'react-router'
import UserService from '../../services/UserService';
import Divider from 'material-ui/Divider';
import FlatButton from 'material-ui/FlatButton';
import FontIcon from 'material-ui/FontIcon';
import Paper from 'material-ui/Paper';
import Checkbox from 'material-ui/Checkbox';

export class BookingHistory extends React.Component {
    constructor() {
        super();
        this.navigateBack = this.navigateBack.bind(this);
    }

    componentDidMount() {

    }

    navigateBack() {
        this.props.router.replace('/profile');
    }

    render() {
        return (
            <div>
                <main className="seminar-history">
                    <span className="back-button">
                            <FlatButton onClick={this.navigateBack} label="Zurück zum Profil"
                                        icon={<FontIcon
                                            className="material-icons">navigate_before</FontIcon>}/>
                        </span>
                    Historie
                </main>
            </div>
        );
    }

}