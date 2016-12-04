import React from 'react';
import EmployeeService from '../../services/EmployeeService';

export class DevisionHeadOverview extends React.Component {
    constructor() {
        super();
        this.renderEmployee = this.renderEmployee.bind(this);
        this.state = {
            employee: [],
            filteredEmployee: [],
        }
    }
    componentDidMount() {
        let empl = {
            "authorities": "string",
                "bookings": [
                {
                    "created": 0,
                    "id": 0,
                    "seminarId": 0,
                    "status": "DENIED",
                    "updated": 0,
                    "username": "string"
                }
            ],
                "division": "FIT",
                "username": "TestUserMan"
        }/*EmployeeService.getAllEmployee();*/
  /*      empl.then(
            result => {
                this.saveEmployee(result)
            }
        );*/ this.saveEmployee(empl)
    };

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