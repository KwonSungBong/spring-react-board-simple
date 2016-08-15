/**
 * Created by 권성봉 on 2016. 8. 1..
 */
import React, {PropTypes, Component} from 'react';
import {Grid} from 'react-bootstrap';

export default class Post extends Component {
    static propTypes = {
        children: PropTypes.object.isRequired
    }

    render() {
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