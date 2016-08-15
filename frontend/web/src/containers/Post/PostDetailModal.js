/**
 * Created by ksb on 2016-08-15.
 */
import React, {Component, PropTypes} from 'react';
import {Modal, Button} from 'react-bootstrap';

import {connect} from 'react-redux';
import {push as pushState} from 'react-router-redux';
import {remove} from 'redux/reducers/post';

export default class PostDetailModal extends Component {
    render() {
        const {post, isDetailModal, pushState, remove, hideDetailModal} = this.props;
        const wordBreakStyle = {wordBreak: 'break-all'};

        return (
            <Modal
                style={wordBreakStyle}
                show={isDetailModal}
                onHide={() => hideDetailModal()}
                container={this}
                aria-labelledby="contained-modal-title"
                >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title">{post.subject}</Modal.Title>
                </Modal.Header>
                <Modal.Body dangerouslySetInnerHTML={{__html: post.content}}>
                </Modal.Body>
                <Modal.Footer>
                    <Button bsStyle="primary" onClick={() => pushState('/post/form/' + post.idx)}>
                        수정
                    </Button>
                    <Button bsStyle="danger" onClick={() => remove(post.idx).then(() => hideDetailModal())}>
                        삭제
                    </Button>
                    <Button onClick={() => hideDetailModal()}>Close</Button>
                </Modal.Footer>
            </Modal>
        )
    }
}
