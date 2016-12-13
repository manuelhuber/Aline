import React from 'react';
import EmployeeService from '../../services/EmployeeService';
import {EmployeeListItem} from './EmployeeListItem';
import {Popover, PopoverAnimationVertical} from 'material-ui/Popover';

export class EmployeeList extends React.Component {
    constructor() {
        super();
        this.renderEmployee = this.renderEmployee.bind(this);
        this.state = {
            employee: []
        }
    }
    componentDidMount() {
        let employ = EmployeeService.getEmployeesForCurrentDivision();
        employ.then(
            result => {
                this.saveEmployee(result)
            }
        );
    }

    saveEmployee(result) {
        this.setState({
            employee: result
        })
    }



    render() {
        return (
            <div>
                <main className="employee-names">
                    {this.state.employee.map(this.renderEmployee)}
                { this.state.employee.length < 1 &&
                <Popover className="no-employee-found">
                    <i className="material-icons md-36">sentiment_neutral</i>
                    <p title="Ja, schlechte Sprüche sind cool!">Ein Satz mit X das war wohl Nix.</p>
                </Popover>}
                </main>
            </div>
        );
    }

    renderEmployee(currentEmployee) {
        return <EmployeeListItem employee={currentEmployee}
                                key={currentEmployee.id}/>;
    }
}