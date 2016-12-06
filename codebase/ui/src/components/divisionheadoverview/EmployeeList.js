import React from 'react';
import EmployeeService from '../../services/EmployeeService';
import {EmployeeListItem} from './EmployeeListItem';

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
                <main className="employee-name">
                    {this.state.employee.map(this.renderEmployee)}
                </main>
                <h3>Entschuldige. TEST.
                </h3>
            </div>
        );
    }

    renderEmployee(currentEmployee) {
        return <EmployeeListItem employee={currentEmployee}
                                key={currentEmployee.id}/>;
    }
}