/**
 * Created by 권성봉 on 2016. 8. 1..
 */
import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import {Grid, Row, Col, Thumbnail, ListGroup, ListGroupItem, Media, Pagination, ButtonGroup, Button, Glyphicon} from 'react-bootstrap';
import {connect} from 'react-redux';
import {asyncConnect} from "redux-connect";
import {push as pushState} from 'react-router-redux';
import {findList, moreList, findOne, remove, showDetailModal, hideDetailModal, showFormModal, hideFormModal} from 'redux/reducers/post';
import PostDetailModal from './PostDetailModal';

@asyncConnect([{
    promise: ({store : {dispatch, getState}}) => {
        return dispatch(findList());
    }
}])
@connect(
    state => ({
        loading: state.post.loading,
        postList: state.post.postList || {},
        post: state.post.post || {},
        isDetailModal: state.post.isDetailModal
    }),
    {pushState, moreList, findOne, remove, showDetailModal, hideDetailModal}
)
export default class PostList extends Component {
    static propTypes = {
        postList: PropTypes.object.isRequired
    }

    componentDidMount() {
        window.addEventListener('scroll', this.handleScroll.bind(this));
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleScroll.bind(this));
    }

    handleScroll(event) {
        const {loading, postList: {number, last}, moreList} = this.props;
        const scrollTop = Math.ceil(event.srcElement.body.scrollTop);
        const height = document.documentElement.offsetHeight - document.documentElement.clientHeight;

        if(!loading && !last && scrollTop === height){
            moreList(number + 1);
        }
    }

    render() {
        const {loading, postList, post, isDetailModal, pushState, findOne, remove, showDetailModal, hideDetailModal} = this.props;
        const wordBreakStyle = {wordBreak: 'break-all'};
        const cursorStyle = {cursor: 'pointer'};
        const buttonStyle = {
            position:'fixed',
            bottom:'20px',
            right: '20px'
        };

        return (
            <Grid>
                <Row className="show-grid">
                    {
                        postList.content && postList.content.length > 0 && postList.content.map((post, i) =>
                            <Col key={i} xs={12} sm={6} md={4} lg={3}>
                                <Thumbnail style={{...wordBreakStyle, ...cursorStyle}} src={post.summaryImage} alt=""
                                           onClick={() => findOne(post.idx).then(() => showDetailModal())}>
                                    <h3>{post.subject}</h3>
                                    <p>{post.summaryContent}</p>
                                </Thumbnail>
                            </Col>
                        )
                    }
                </Row>
                {
                    loading && <Row>로딩중...</Row>
                }

                <Button style={buttonStyle} onClick={() => pushState('/post/form/')}>
                    <Glyphicon glyph="pencil" />
                </Button>
                <PostDetailModal post={post} pushState={pushState} remove={remove} isDetailModal={isDetailModal} hideDetailModal={hideDetailModal} />
            </Grid>
        )
    }
}