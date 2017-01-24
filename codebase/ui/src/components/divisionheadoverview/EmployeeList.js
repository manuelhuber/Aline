import React from "react";
import Util from "../../services/Util";
import EmployeeService from "../../services/EmployeeService";
import {EmployeeListItem} from "./EmployeeListItem";
import {SearchBar} from "../general/SearchBar";
import {Popover, PopoverAnimationVertical} from "material-ui/Popover";
import BookingService from "../../services/BookingService";

export class EmployeeList extends React.Component {
    constructor() {
        super();
        this.renderEmployee = this.renderEmployee.bind(this);
        this.clearFilter = this.clearFilter.bind(this);
        this.filterEmployees = this.filterEmployees.bind(this);
        this.showRelevantEmployees = this.showRelevantEmployees.bind(this);
        this.grantSingleBooking = this.grantSingleBooking.bind(this);
        this.grantAllBookings = this.grantAllBookings.bind(this);
        this.checkForUngrantedBookings = this.checkForUngrantedBookings.bind(this);
        this.state = {
            employees: [],
            filteredEmployees: [],
            filteredEmployeesBackup: [],
            showRelevantEmployees: false,
            totalIssuedSpending: 0,
            totalPlannedSpending: 0,
        }
    }

    componentDidMount() {
        let employ = EmployeeService.getEmployeesForCurrentDivision();
        employ.then(
            result => {
                this.saveEmployee(result);
                this.calculateSeminareTotalAmount(result)
            }
        )
    }

    calculateSeminareTotalAmount(employees) {
        var totalIssuedSpendings = 0;
        var totalPlannedSpendings = 0;
        employees.map(employee => {
                if (employee.bookings.length > 0) {
                    totalIssuedSpendings += Util.formatMoneyFromCent(employee.bookings[0].issuedSpending);
                    totalPlannedSpendings += Util.formatMoneyFromCent(employee.bookings[0].plannedTotalSpending);
                }
            }
        );
        this.setState({
            totalIssuedSpending: totalIssuedSpendings,
            totalPlannedSpending: totalPlannedSpendings
        })
    }


    saveEmployee(result) {
        this.setState({
            employees: result,
            filteredEmployees: result
        })
    }

    filterEmployees(name) {
        let filteredEmployees = this.state.employees.filter(employee => JSON.stringify(employee).toLowerCase().includes(name.toLowerCase()));
        this.setState({
            filteredEmployees: filteredEmployees
        })
    }

    clearFilter() {
        this.setState({
            filteredEmployees: this.state.employees
        })
    }

    showRelevantEmployees(showRelevant) {
        this.setState({
            showRelevantEmployees: showRelevant
        });
        if (showRelevant === true) {
            let filtered = this.state.filteredEmployees.filter(employee => JSON.stringify(employee.bookings).toUpperCase().includes('REQUESTED'));
            this.setState({
                filteredEmployeesBackup: this.state.filteredEmployees,
                filteredEmployees: filtered
            })
        }
        else {
            this.setState({
                filteredEmployees: this.state.filteredEmployeesBackup,
            })
        }
    }

    grantAllBookings(bookings) {
        var response = BookingService.grantAllBookings(bookings);
        response.then(
            result => {
                var employee = EmployeeService.getEmployeesForCurrentDivision();
                employee.then(
                    result => {
                        this.saveEmployee(result);
                        this.calculateSeminareTotalAmount(result)
                    }
                );
                this.props.showSnackbar('Seminarbuchungen erfolgreich bestätigt.');
            },
            failureResult => {
                this.props.router.replace('/error');
            }
        )
    }

    grantSingleBooking(bookingId) {
        var response = BookingService.grantSingleBooking(bookingId);
        response.then(
            result => {
                var employee = EmployeeService.getEmployeesForCurrentDivision();
                employee.then(
                    result => {
                        this.saveEmployee(result);
                        this.calculateSeminareTotalAmount(result)
                    }
                );
                this.props.showSnackbar('Seminarbuchung erfolgreich bestätigt.');
            },
            failureResult => {
                this.props.router.replace('/error');
            }
        )
    }

    checkForUngrantedBookings(bookings) {
        if (bookings.length != 0) {
            if (bookings[0].grantedSpending == bookings[0].plannedTotalSpending) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    render() {
        return (
            <div className="division">
                <SearchBar searchBarType="department" searchForText={this.filterEmployees}
                           showRelevantEmployees={this.showRelevantEmployees}
                           clearFilter={this.clearFilter}
                />
                <div className="division-budget output-properties">
                    <div className="property">
                        <output title="Das bereits verbrauchte Budget">
                            <label>Verbrauchtes Budget:</label>
                            {this.state.totalIssuedSpending} €
                        </output>
                    </div>
                    <div className="property">
                        <output title="Die geplanten Ausgaben">
                            <label>Geplantes Budget:</label>
                            {this.state.totalPlannedSpending} €
                        </output>
                    </div>
                </div>
                <main className="employees">
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
        return <EmployeeListItem employee={currentEmployee} router={this.props.router}
                                 key={currentEmployee.userName}
                                 confirmSingleBooking={this.grantSingleBooking}
                                 confirmAllBookings={this.grantAllBookings}
                                 checkForUngrantedBookings={this.checkForUngrantedBookings}/>;
    }
}