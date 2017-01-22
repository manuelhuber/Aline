import React from 'react';
import SeminarService from '../../services/SeminarService';

export class PrintableInvoice extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            bill: {}
        }
    }

    componentDidMount(){
        let bill = SeminarService.generateBillForSeminar(this.props.params.seminarId);
        bill.then(
            result => {
                this.setState({
                    bill: result
                })
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    render() {
        return(
            <div>Your Invoice for the seminar with id {this.props.params.seminarId} here</div>
        )
    }
}

