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
        this.showRelevantEmployees = this.showRelevantEmployees.bind(this);
        this.state = {
            employees: [],
            filteredEmployees: [],
            relevantEmployees: false
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

    showRelevantEmployees(showRelevant){
        this.setState({
            showRelevantEmployees: showRelevant
        })
        if (showRelevant === true) {
            let filtered = this.state.filteredEmployees.filter(employee => JSON.stringify(employee.bookings).toUpperCase().includes('REQUESTED'));
            this.setState({
                filteredEmployees: filtered
            })
        }
        else {
            this.setState({
                filteredEmployees: this.state.employees
            })
        }
    }

    saveRelevantEmployee(employee){
        console.log(employee)
        this.setState({
            filteredEmployees : employee
        })
    }

    render() {
        return (
            <div>
                <SearchBar searchBarType="department" searchForText={this.filterEmployees}
                           showRelevantEmployees={this.showRelevantEmployees}
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