/**
 * Created by 권성봉 on 2016. 8. 1..
 */
import React, {Component, PropTypes} from 'react';
import {ListGroup, ListGroupItem, Media, Pagination, ButtonGroup, Button} from 'react-bootstrap';
import {connect} from 'react-redux';
import {asyncConnect} from "redux-connect";
import {push as pushState} from 'react-router-redux';
import {findList, remove} from 'redux/reducers/post';

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
    {pushState, remove}
)
export default class PostList extends Component {
    static propTypes = {
        postList: PropTypes.object.isRequired,
        pushState: PropTypes.func.isRequired,
        remove: PropTypes.func.isRequired
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
        const {loading, postList, pushState} = this.props;
        const bodyStyle = {wordBreak: 'break-all'};

        return (
            <div>
                <ListGroup>
                    {
                        postList.content && postList.content.length > 0 && postList.content.map((post, i) =>
                            <ListGroupItem key={i}>
                                <Media>
                                    <Media.Left>
                                        <img width={64} height={64} src="http://www.carlab.co.kr/file/downImg/FILE_000000000026150/1.jpg" alt="Image"/>
                                    </Media.Left>
                                    <Media.Body>
                                        <Media.Heading style={bodyStyle}>{post.subject}</Media.Heading>
                                        <p style={bodyStyle}>{post.summaryContent}</p>
                                    </Media.Body>
                                    <Media.Right>
                                        <ButtonGroup className="pull-right">
                                            <Button disabled={loading} onClick={() => pushState('/post/form/' + post.idx)}>수정</Button>
                                            <Button disabled={loading} onClick={() => this.removeAction(post.idx)}>삭제</Button>
                                        </ButtonGroup>
                                    </Media.Right>
                                </Media>
                            </ListGroupItem>
                        )
                    }
                </ListGroup>
                <div className="text-center">
                    <Pagination
                        first={postList.first}
                        last={postList.last}
                        boundaryLinks
                        items={postList.totalPages}
                        maxButtons={5}
                        activePage={postList.number + 1}
                        onSelect={(e, se) => pushState('/post/page/' + e)}
                    />
                </div>
            </div>
        )
    }
}