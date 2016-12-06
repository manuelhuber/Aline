import React from 'react';
import EmployeeService from '../../services/EmployeeService';
import {EmployeeListItem} from './EmployeeListItem';

export class EmployeeList extends React.Component {
    constructor() {
        super();
        this.renderEmployees = this.renderEmployees.bind(this);
        this.saveEmployees = this.saveEmployees.bind(this);
        this.state = {
            employees: []
        }
    }

    saveEmployees(result) {
        this.setState({
            employees: result,
        })
    }

    componentDidMount() {
        let employ = EmployeeService.getEmployeesForCurrentDivision();
        employ.then(
            result => {
                this.saveEmployees(result)
            }
        );
    }

    render() {
        return (
            <div>
                <main className="employee-name">
                    {this.state.employees.map(this.renderEmployees)}
                </main>
                <h3>Entschuldige. TEST.
                </h3>
            </div>
        );
    }

    renderEmployees(currentEmployee) {
        return <EmployeeListItem employee={currentEmployee}
                                 key={currentEmployee.userName}/>;
    }
}