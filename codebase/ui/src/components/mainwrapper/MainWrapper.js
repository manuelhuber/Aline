/**
 * Created by franziskah on 22.11.16.
 */
import React from 'react';
import {Header} from './Header';

export class MainWrapper extends React.Component {
    constructor(props) {
        super(props);
    };

    render() {
        return (
            <div className="main-wrapper">
                <Header />
                <div className="content">
                    {this.props.children}
                </div>
            </div>
        );
    }
}

