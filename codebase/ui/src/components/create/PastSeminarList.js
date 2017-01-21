import React from 'react';
import SeminarService from '../../services/SeminarService';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import FlatButton from 'material-ui/FlatButton';

export class PastSeminarList extends React.Component {
    constructor(props) {
        super(props);
        this.renderRow = this.renderRow.bind(this);
        this.chooseSeminar = this.chooseSeminar.bind(this);
        this.state = {
            seminars: []
        }
    }

    componentDidMount() {
        let seminars = SeminarService.getPastSeminars();
        seminars.then(
            result => {
                this.setState({
                    seminars: result
                })
            })
            .catch(failureResult => {
                this.props.router.replace('/error');
            });
    }

    chooseSeminar(event) {
        this.props.chooseSeminar(event.currentTarget.name);
    }

    renderRow(seminar) {
        return (
            <TableRow key={seminar.id}>
                <TableRowColumn>{seminar.id}</TableRowColumn>
                <TableRowColumn title={seminar.name}>{seminar.name}</TableRowColumn>
                <TableRowColumn title={seminar.trainer}>{seminar.trainer}</TableRowColumn>
                <TableRowColumn title={seminar.category}>{seminar.category}</TableRowColumn>
                <TableRowColumn><FlatButton label="Auswählen" primary={true} onClick={this.chooseSeminar}
                                            name={seminar.id}/></TableRowColumn>
            </TableRow>
        )
    }

    render() {
        const tableStyle = {
            maxHeight: '300px',
            overflowX: 'auto'
        };
        return (
            <Table multiSelectable={false} onRowSelection={this.chooseSeminar} selectable={false}
                   bodyStyle={tableStyle}>
                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                        <TableHeaderColumn>ID</TableHeaderColumn>
                        <TableHeaderColumn>Name</TableHeaderColumn>
                        <TableHeaderColumn>Referent/externer Anbieter</TableHeaderColumn>
                        <TableHeaderColumn>Kategorie</TableHeaderColumn>
                        <TableHeaderColumn>Aktion</TableHeaderColumn>
                    </TableRow>
                </TableHeader>
                <TableBody displayRowCheckbox={false}>
                    {this.state.seminars.map(this.renderRow)}
                </TableBody>
            </Table>
        );
    }
}