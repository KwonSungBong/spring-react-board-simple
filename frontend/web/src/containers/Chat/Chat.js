/**
 * Created by ksb on 2016-08-07.
 */
import React, {PropTypes, Component} from 'react';
import {connect} from 'react-redux';
import {push as pushState} from 'react-router-redux';

@connect(
    state => ({}),
    {}
)
export default class Chat extends Component {
    static propTypes = {}

    render() {
        return (
            <div>
                채팅
            </div>
        )
    }
}