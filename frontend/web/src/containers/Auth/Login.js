/**
 * Created by gwonseongbong on 8/10/16.
 */
import React, {Component} from 'react';
import {Grid, Row, Col, Button, FormControl} from 'react-bootstrap';

import {reduxForm} from 'redux-form';
import {authToken, login, logout} from 'redux/reducers/auth';

@reduxForm(
    {
        form: 'login',
        fields: ['email', 'password']
    },
    state => ({
        loaded: state.auth.loaded,
        user: state.auth.user
    }),
    {authToken, login, logout}
)
export default class Login extends Component {
    render() {
        // const {fields: {email, password}, handleSubmit, values} = this.props;
        const {handleSubmit} = this.props;
        const {loaded, user, authToken, login, logout} = this.props;

        const values = {email: 'default@carlab.co.kr', password: 'Default'};

        return (
            <div>
                {
                    !loaded &&
                    <Grid>
                        <Row>
                            그냥 아이디 비번 미리 입력해놓음
                        </Row>
                        <Row>
                            {/*<FormControl {...email}/>*/}
                            email : {values.email}
                        </Row>
                        <Row>
                            {/*<FormControl {...password}/>*/}
                            password : {values.password}
                        </Row>
                        <Row>
                            <Button onClick={handleSubmit(() => {
                                authToken().then(result => login(values, result.token));
                            })}>
                                로그인
                            </Button>
                        </Row>
                    </Grid>
                }
                {
                    loaded &&
                    <Grid>
                        <Row>
                            userName : {user.userName}
                        </Row>
                        <Row>
                            <Button onClick={handleSubmit(() => {
                                authToken().then(result => logout(result.token));
                            })}>
                                 로그아웃
                            </Button>
                        </Row>
                    </Grid>
                }
            </div>
        )
    }
}