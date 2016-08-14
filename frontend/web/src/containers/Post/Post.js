/**
 * Created by 권성봉 on 2016. 8. 1..
 */
import React, {PropTypes, Component} from 'react';
import {Grid, Panel, Button} from 'react-bootstrap';
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
                        {path && path === 'form' && ' 등록'}
                        {path && path === 'form/:idx' && ' 수정'}
                    </h1>
                    {
                        (!path || path === 'page/:page') &&
                        <Button disabled={loading} className="pull-right" onClick={() => pushState('/post/form')}>
                            등록
                        </Button>
                    }
                </Grid>
                {this.props.children}
            </Grid>
        )
    }
}