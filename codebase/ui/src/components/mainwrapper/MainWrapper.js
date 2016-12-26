/**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';
import {Header} from './Header';
//Material ui
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
//Appwide components
import Snackbar from 'material-ui/Snackbar';
import CircularProgress from 'material-ui/CircularProgress';

export class MainWrapper extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            snackbarOpen: false,
            snackbarMessage: 'Erfolgreich ausgeführt',
            showLoadingIndicator: false
        };
        this.closeSnackbar = this.closeSnackbar.bind(this);
        this.showSnackbar = this.showSnackbar.bind(this);
        this.showLoadingIndicator = this.showLoadingIndicator.bind(this);
    };

    closeSnackbar() {
        this.setState({
            snackbarOpen: false
        })
    }

    /**
     * Show the global Snackbar.
     * Use with 'this.props.showSnackbar('Text to show')' in any child component of this Main Wrapper
     * @param messageToShow the message to show in the snackbar
     */
    showSnackbar(messageToShow) {
        this.setState({
            snackbarMessage: messageToShow,
            snackbarOpen: true
        })
    }

    /**
     * Show or hide the global loading indicator.
     * Use with 'this.props.showLoadingIndicator(true)' in any child component of this Main Wrapper
     * @param show boolean value to decide if to show or hide the loading indicator
     */
    showLoadingIndicator(show) {
        this.setState({
            showLoadingIndicator: show
        })
    }

    render() {
        return (
            <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                <div className="main-wrapper">
                    <Header />
                    <div className="content">
                        {React.cloneElement(this.props.children, {showSnackbar: this.showSnackbar, showLoadingIndicator: this.showLoadingIndicator})}
                    </div>
                    <Snackbar message={this.state.snackbarMessage} open={this.state.snackbarOpen}
                              autoHideDuration={4000} onRequestClose={this.closeSnackbar}/>
                    {this.state.showLoadingIndicator &&
                    <div id="loadingIndicator"><CircularProgress size={80} thickness={5}/></div>}
                </div>
            </MuiThemeProvider>
        );
    }
}

