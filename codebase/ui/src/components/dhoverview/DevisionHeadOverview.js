import React from 'react';

export class DevisionHeadOverview extends React.Component {
    constructor() {
        super();
        this.renderEmplayee = this.renderEmployee.bind(this);
        this.state = {
            employee: [],
            filteredSeminars: [],
        }
    }

    render() {
        return (
            <div>
                <main className="employee-name">
                    {this.employee.filteredEmployee.map(this.renderStaff)}
                </main>
                <h3>Entschuldige. TEST.
                </h3>
            </div>
        );
    }
    renderEmployee(currentEmployee) {
        return <SeminarListItem seminar={currentEmployee}
                                key={currentEmployee.id}/>;
    }
}