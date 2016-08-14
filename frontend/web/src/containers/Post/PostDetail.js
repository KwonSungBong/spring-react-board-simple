/**
 * Created by ksb on 2016-08-14.
 */
import React, {Component, PropTypes} from 'react';
import {Grid, Panel, ButtonGroup, Button} from 'react-bootstrap';

import {connect} from 'react-redux';
import {asyncConnect} from 'redux-connect';
import {push as pushState} from 'react-router-redux';
import {findOne, remove} from 'redux/reducers/post';

@asyncConnect([
    {
        promise: ({store : {dispatch, getState}, params}) => {
            return dispatch(findOne(params.idx));
        }
    }
])
@connect(
    state => ({
        page: state.post.postList.number ? state.post.postList.number + 1 : 1,
        post: state.post.post
    }),
    {pushState, remove}
)
export default class PostDetail extends Component {
    static propTypes = {}

    render() {
        const {pushState, remove, post, page} = this.props;
        const wordBreakStyle = {wordBreak: 'break-all'};

        return (
            <Grid>
                <Panel style={wordBreakStyle} header={post.subject}>
                    <div dangerouslySetInnerHTML={{__html: post.content}}/>
                </Panel>
                <ButtonGroup className="pull-right">
                    <Button bsStyle="primary" onClick={() => pushState('/post/form/' + post.idx)}>
                        수정
                    </Button>
                    <Button bsStyle="danger" onClick={() => remove(post.idx).then(pushState('/post/page/' + page))}>
                        삭제
                    </Button>
                    <Button onClick={() => pushState('/post/page/' + page)}>
                        취소
                    </Button>
                </ButtonGroup>
            </Grid>
        )
    }
}