import React from 'react';
import {Link} from 'react-router'
import {SeminarListItem} from './SeminarListItem';
import {SearchBar} from '../general/SearchBar';
import SeminarService from '../../services/SeminarService';
import AuthService from '../../services/AuthService';

export class SeminarList extends React.Component {
    constructor() {
        super();
        this.renderSeminar = this.renderSeminar.bind(this);
        this.renderPastSeminar = this.renderPastSeminar.bind(this);
        this.saveSeminars = this.saveSeminars.bind(this);
        this.savePastSeminars = this.savePastSeminars.bind(this);
        this.searchForText = this.searchForText.bind(this);
        this.filterSeminars = this.filterSeminars.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
        this.showPastSeminars = this.showPastSeminars.bind(this);
        this.state = {
            seminars: [],
            filteredSeminars: [],
            pastSeminars: [],
            isFrontOffice: false,
            showPastSeminars: false
        }
    }

    componentDidMount() {
        this.setState({
            isFrontOffice: AuthService.isFrontOffice()
        });
        this.props.showLoadingIndicator(true);
        let seminars = SeminarService.getCurrentSeminars();
        seminars.then(
            result => {
                this.saveSeminars(result);
                this.props.showLoadingIndicator(false)
            },
            failureResult => {
                this.props.router.replace('/error');
            }
        );
        if (AuthService.isFrontOffice()) {
            this.props.showLoadingIndicator(true);
            let pastSeminars = SeminarService.getPastSeminars();
            pastSeminars.then(
                result => {
                    this.savePastSeminars(result);
                    this.props.showLoadingIndicator(false)
                },
                failureResult => {
                    this.props.router.replace('/error');
                }
            );
        }
    }

    saveSeminars(result) {
        this.setState({
            seminars: result,
            filteredSeminars: result
        })
    }

    savePastSeminars(result) {
        this.setState({
            pastSeminars: result
        })
    }

    showPastSeminars(showPastSeminars) {
        this.setState({
            showPastSeminars: showPastSeminars
        });
        if (showPastSeminars === true) {
            let pastSeminars = document.getElementsByClassName('past-seminar');
            for (let i = 0; i < pastSeminars.length; i++) {
                pastSeminars[i].classList.remove('invisible');
            }
        }
        else {
            let pastSeminars = document.getElementsByClassName('past-seminar');
            for (let i = 0; i < pastSeminars.length; i++) {
                pastSeminars[i].classList.add('invisible');
            }
        }
    }

    searchForText(textToSearchFor) {
        let filteredSeminars = this.state.seminars.filter(seminar => JSON.stringify(seminar).toLowerCase().includes(textToSearchFor.toLowerCase()));
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    filterSeminars(category, targetLevel) {
        let filteredSeminars = [];
        //Category yes && targetLevel no
        if (category && !targetLevel) {
            filteredSeminars = this.state.seminars.filter(seminar => seminar.category === category);
        }
        //Category no && targetLevel yes
        else if (!category && targetLevel) {
            filteredSeminars = this.state.seminars.filter(seminar => seminar.targetLevel.includes(targetLevel));
        }
        //Category yes && targetLevel yes
        else if (category && targetLevel) {
            filteredSeminars = this.state.seminars.filter(seminar => seminar.category === category);
            filteredSeminars = filteredSeminars.filter(seminar => seminar.targetLevel.includes(targetLevel));
        }
        this.setState({
            filteredSeminars: filteredSeminars
        })
    }

    clearFilter() {
        this.setState({
            filteredSeminars: this.state.seminars
        })
    }

    render() {
        return (
            <div>
                <SearchBar searchBarType="overview" filterSeminars={this.filterSeminars}
                           showPastSeminars={this.showPastSeminars}
                           clearFilter={this.clearFilter} searchForText={this.searchForText}/>
                <main className="seminar-tiles">
                    {this.state.isFrontOffice &&
                    <div className="add-seminar-tile">
                        <Link to={`create`}>
                            <i className="material-icons">add</i>
                        </Link>
                    </div>
                    }
                    { this.state.filteredSeminars.map(this.renderSeminar) }
                    { this.state.isFrontOffice && this.state.pastSeminars.map(this.renderPastSeminar) }
                    { ((this.state.filteredSeminars.length < 1 ) && ((this.state.pastSeminars.length < 1) || !(this.state.showPastSeminars))) &&
                    <div className="no-seminar-found">
                        <i className="material-icons md-36">sentiment_neutral</i>
                        <p title="Ja, schlechte Sprüche sind cool!">Ein Satz mit X das war wohl Nix.</p>
                    </div>}
                </main>
            </div>
        );
    }

    renderSeminar(currentSeminar) {
        return <SeminarListItem seminar={currentSeminar}
                                key={currentSeminar.id}/>;
    }

    renderPastSeminar(currentSeminar) {
        return <SeminarListItem seminar={currentSeminar} isPast={true}
                                key={currentSeminar.id}/>;
    }

}