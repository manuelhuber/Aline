/**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';
import {Header} from './Header';
//Material ui
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';

export class MainWrapper extends React.Component {
    constructor(props) {
        super(props);
    };

    render() {
        return (
            <div className="main-wrapper">
                <Header />
                <div className="content">
                    <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                        {this.props.children}
                    </MuiThemeProvider>
                </div>
            </div>
        );
    }
}

