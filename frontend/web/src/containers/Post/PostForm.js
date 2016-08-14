/**
 * Created by 권성봉 on 8/2/16.
 */
import React, {Component, PropTypes} from 'react';
import ReactDOM from 'react-dom';
import {Form, FormControl, FormGroup, ControlLabel, HelpBlock, Panel, Grid, Row, Col, ButtonGroup, Button, Thumbnail} from 'react-bootstrap';

import {connect} from 'react-redux';
import {reduxForm} from 'redux-form';
import {asyncConnect} from 'redux-connect';
import {push as pushState} from 'react-router-redux';

import {createValidator, required} from '../../helpers/validation';
import {resetOne, findOne, create, update} from 'redux/reducers/post';

@asyncConnect([
    {
        promise: ({store : {dispatch, getState}, params}) => {
            if(params.idx){
                return dispatch(findOne(params.idx));
            } else {
                return dispatch(resetOne());
            }
        }
    }
])
@connect(
    state => ({
        post: state.post.post
    })
)
export default class PostFormWrapper extends Component {
    static propTypes = {
        post: PropTypes.object
    }

    render(){
        const {post} = this.props;

        return (
            <Grid>
                <PostForm initialValues={post} />
            </Grid>
        )
    }
}

if (__CLIENT__) {
    global.jQuery = require('jquery');
}
let promise = {};
let loaded = {};
function load(path, callback) {
    if (loaded[path]) return callback();

    if (!promise[path]) {
        promise[path] = jQuery.getScript(path);
    }

    var cb = function () {
        loaded[path] = true;
        delete promise[path];
        callback();
    };

    promise[path].done(cb).fail(cb);
}

function checkBlank(value) {
    if (value && value.trim() === '') {
        return '공백만 입력은 안됩니다.';
    }
}

@reduxForm(
    {
        form: 'PostForm',
        fields: ['idx', 'subject', 'content', 'imageGroup.imageGroupIdx', 'imageGroup.imageList[].imageIdx'],
        validate: createValidator({subject:[required,checkBlank]})
    },
    state => ({
        loading: state.post.loading,
        page: state.post.postList.number ? state.post.postList.number + 1 : 1,
        post: state.post.post
    }),
    {pushState, create, update}
)
class PostForm extends Component {
    static propTypes = {}

    componentDidMount(){
        const {fields: {imageGroup: {imageList}}} = this.props;

        window.CKEDITOR_BASEPATH = '/ckeditor/';

        load('/ckeditor/ckeditor.js', () => {
            if(this.refs.editor){
                this.editor = CKEDITOR.replace(ReactDOM.findDOMNode(this.refs.editor));
                this.editor.config.clipboard_defaultContentType = 'text';
                this.editor.config.ignoreEmptyParagraph = false;
                this.editor.config.extraPlugins = 'imageresponsive';
                this.editor.config.image_prefillDimensions = false;
                this.editor.config.image2_prefillDimensions = false;
                this.editor.config.height = 400;
                var fileIdSetting = CKEDITOR.tools.addFunction(function (response) {
                    imageList.addField(response);
                }.bind(this));

                this.editor.config.filebrowserImageUploadUrl = 'http://localhost:8080/image/ckeditor-upload?fileIdSetting=' + fileIdSetting;
                this.editor.config.removeButtons = 'BrowseServer';
            }
        });
    }

    componentWillUnmount(){
        if (this.editor && CKEDITOR && CKEDITOR.instances && CKEDITOR.instances.content) {
            CKEDITOR.instances.content.destroy();
        }
    }

    render() {
        const {fields: {idx, subject, content}, values, handleSubmit, submitting, invalid} = this.props;
        const {loading, pushState, create, update, page} = this.props;
        const wordBreakStyle = {wordBreak: 'break-all'};

        const header = (
            <FormGroup controlId="subject" validationState={subject.visited && subject.invalid ? 'error' : ''}>
                <FormControl type="text" placeholder="제목" {...subject} />
                {subject.touched && subject.error && <HelpBlock>{subject.error}</HelpBlock>}
            </FormGroup>
        );

        return (
            <Form horizontal onSubmit={handleSubmit}>
                <Panel style={wordBreakStyle} header={header}>
                    <FormGroup controlId="content">
                        <FormControl ref="editor" componentClass="textarea" rows="10" {...content} />
                    </FormGroup>
                </Panel>
                <ButtonGroup className="pull-right">
                    <Button type="submit" disabled={submitting || invalid || loading}
                            bsStyle="success" onClick={handleSubmit(() => {
                        values.content = this.editor.getData();
                        if(idx.value){
                            update(values).then(result => pushState('/post/detail/' + idx.value));
                        } else {
                            create(values).then(result => pushState('/post'));
                        }
                    })}>
                        저장
                    </Button>
                    <Button disabled={loading} onClick={() => pushState('/post/page/' + page)}>
                        취소
                    </Button>
                </ButtonGroup>
            </Form>
        )
    }
}