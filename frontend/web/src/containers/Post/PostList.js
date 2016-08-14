/**
 * Created by 권성봉 on 2016. 8. 1..
 */
import React, {Component, PropTypes} from 'react';
import {Grid, Row, Col, Thumbnail, ListGroup, ListGroupItem, Media, Pagination, ButtonGroup, Button} from 'react-bootstrap';
import {connect} from 'react-redux';
import {asyncConnect} from "redux-connect";
import {push as pushState} from 'react-router-redux';
import {findList, moreList} from 'redux/reducers/post';

@asyncConnect([{
    promise: ({store : {dispatch, getState}, params}) => {
        if (params.page) {
            return dispatch(findList(Number(params.page) - 1));
        } else {
            return dispatch(findList());
        }
    }
}])
@connect(
    state => ({
        loading: state.post.loading,
        postList: state.post.postList || {}
    }),
    {pushState, moreList}
)
export default class PostList extends Component {
    static propTypes = {
        postList: PropTypes.object.isRequired
    }

    removeAction(idx){
        const {postList: {content}, pushState, remove} = this.props;
        remove(idx);

        let page =  this.props.params.page;

        if(page && content.length === 1){
            page--;
        } else if(!page) {
            page = 0;
        }

        if(page < 1){
            pushState('/post');
        } else {
            pushState('/post/page/' + page);
        }
    }

    render() {
        const {postList, pushState, moreList} = this.props;
        const wordBreakStyle = {wordBreak: 'break-all'};
        const cursorStyle = {cursor: 'pointer'};

        return (
            <Grid>
                <Row className="show-grid">
                    {
                        postList.content && postList.content.length > 0 && postList.content.map((post, i) =>
                            <Col key={i} xs={12} sm={6} md={4} lg={3}>
                                <Thumbnail style={wordBreakStyle} src={post.summaryImage} alt=""
                                           onClick={() => pushState('/post/detail/' + post.idx)}>
                                    <h3>{post.subject}</h3>
                                    <p>{post.summaryContent}</p>
                                </Thumbnail>
                            </Col>
                        )
                    }
                </Row>
                {
                    !postList.last &&
                    <Row>
                        <Button onClick={() => moreList(postList.number + 1)}>더보기</Button>
                    </Row>
                }
            </Grid>
        )
    }
}