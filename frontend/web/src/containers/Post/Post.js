/**
 * Created by 권성봉 on 2016. 8. 1..
 */
import React, {PropTypes, Component} from 'react';
import {Grid} from 'react-bootstrap';
import {connect} from 'react-redux';
import {push as pushState} from 'react-router-redux';

@connect(
    state => ({
        loading: state.post.loading
    }),
    {pushState}
)
export default class Post extends Component {
    static propTypes = {
        children: PropTypes.object.isRequired,
        pushState: PropTypes.func.isRequired
    }

    render() {
        const {loading, pushState} = this.props;
        const {props: {route: {path}}} = this.props.children;

        return (
            <Grid>
                <Grid>
                    <h1 className="pull-left">
                        포스트
                    </h1>
                </Grid>
                {this.props.children}
            </Grid>
        )
    }
}