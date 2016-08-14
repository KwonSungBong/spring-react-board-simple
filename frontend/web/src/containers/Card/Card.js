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
export default class Card extends Component {
    static propTypes = {}

    render(){
        return (
            <div>
                카드
            </div>
        )
    }
}