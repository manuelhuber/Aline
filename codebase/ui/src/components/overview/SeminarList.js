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
            shownSeminars: [],
            pastSeminars: [],
            shownPastSeminars: [],
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
                this.props.showLoadingIndicator(false);
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });

        if (AuthService.isFrontOffice()) {
            this.props.showLoadingIndicator(true);
            let pastSeminars = SeminarService.getPastSeminars();
            pastSeminars.then(
                result => {
                    this.savePastSeminars(result);
                    this.props.showLoadingIndicator(false);
                })
                .catch(failureResult => {
                    this.props.router.replace('/error');
                });
        }
    }

    saveSeminars(result) {
        this.setState({
            seminars: result,
            shownSeminars: result
        })
    }

    savePastSeminars(result) {
        this.setState({
            pastSeminars: result,
            shownPastSeminars: result
        })
    }

    showPastSeminars(showPastSeminars) {
        this.setState({
            showPastSeminars: showPastSeminars
        });
    }

    searchForText(textToSearchFor) {
        let shownSeminars = this.state.seminars.filter(seminar => JSON.stringify(seminar).toLowerCase().includes(textToSearchFor.toLowerCase()));
        this.setState({
            shownSeminars: shownSeminars
        })
    }

    filterSeminars(category, targetLevel) {
        let shownSeminars = [];
        let shownPastSeminars = [];
        //Category yes && targetLevel no
        if (category && !targetLevel) {
            shownSeminars = this.state.seminars.filter(seminar => seminar.category === category);
            shownPastSeminars = this.state.pastSeminars.filter(seminar => seminar.category === category);
        }
        //Category no && targetLevel yes
        else if (!category && targetLevel) {
            shownSeminars = this.state.seminars.filter(seminar => seminar.targetLevel.includes(targetLevel));
            shownPastSeminars = this.state.pastSeminars.filter(seminar => seminar.targetLevel.includes(targetLevel));
        }
        //Category yes && targetLevel yes
        else if (category && targetLevel) {
            shownSeminars = this.state.seminars.filter(seminar => seminar.category === category);
            shownSeminars = shownSeminars.filter(seminar => seminar.targetLevel.includes(targetLevel));

            shownPastSeminars = this.state.pastSeminars.filter(seminar => seminar.category === category);
            shownPastSeminars = shownPastSeminars.filter(seminar => seminar.targetLevel.includes(targetLevel));
        }
        this.setState({
            shownSeminars: shownSeminars,
            shownPastSeminars: shownPastSeminars
        });
    }

    clearFilter() {
        this.setState({
            shownSeminars: this.state.seminars,
            shownPastSeminars: this.state.pastSeminars
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
                    <div className="add-seminar-tile" title="Klicke hier um ein neues Seminar anzulegen.">
                        <Link to={`create`}>
                            <i className="material-icons">add</i>
                        </Link>
                    </div>
                    }
                    { this.state.shownSeminars.map(this.renderSeminar) }
                    { (this.state.isFrontOffice && this.state.showPastSeminars) && this.state.shownPastSeminars.map(this.renderPastSeminar) }
                    { ((this.state.shownSeminars.length < 1 ) && ((this.state.shownPastSeminars.length < 1) || !(this.state.showPastSeminars))) &&
                    <div className={this.state.isFrontOffice? 'no-seminar-found-frontoffice' : 'no-seminar-found'}>
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