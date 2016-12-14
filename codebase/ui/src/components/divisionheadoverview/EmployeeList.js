import React from 'react';
import EmployeeService from '../../services/EmployeeService';
import {EmployeeListItem} from './EmployeeListItem';
import {SearchBar} from '../general/SearchBar';
import {Popover, PopoverAnimationVertical} from 'material-ui/Popover';

export class EmployeeList extends React.Component {
    constructor() {
        super();
        this.renderEmployee = this.renderEmployee.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
        this.filterEmployees = this.filterEmployees.bind(this);
        this.state = {
            employees: [],
            filteredEmployees: []
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
            employees: result,
            filteredEmployees: result
        })
    }

    filterEmployees(name) {
        let filteredEmployees = this.state.employees.filter(employee => JSON.stringify(employee).toLowerCase().includes(name.toLowerCase()));
    this.setState ({
            filteredEmployees: filteredEmployees
        })
    }

    clearFilter(){
        this.setState({
            filteredEmployees: this.state.employees
        })
    }

    render() {
        return (
            <div>
                <SearchBar searchBarType="department" searchForText={this.filterEmployees}
                           clearFilter={this.clearFilter}/>
                <main className="employee-names">
                    {this.state.filteredEmployees.map(this.renderEmployee)}
                { this.state.filteredEmployees.length < 1 &&
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