import React from 'react';
import EmployeeService from '../../services/EmployeeService';
import EmployeeListItem from './EmployeeListItem';

export class EmployeeList extends React.Component {
    constructor() {
        super();
        this.renderEmployee = this.renderEmployee.bind(this);
        this.state = {
            employee: [],
            filteredEmployee: [],
        }
    }
    componentDidMount() {
        let employ = EmployeeService.getAllEmployee();
        employ.then(
            result => {
                this.saveEmployee(result)
            }
        );
    }

    saveEmployee(result) {
        this.setState({
            employee: result,
            filteredEmployee: result
        })
    }

/*    filterEmployee() {
        let filtered = [];
        let devision = EmployeeService.getCurrentUserDevision()
        //Employee belongs to DevisionHead
            filtered = this.state.employee.filter(employee => employee.devision === devision);

        this.setState({
            filteredEmployee: filtered
        })
    }*/

    clearFilter() {
        this.setState({
            filteredEmployee: this.state.employee
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