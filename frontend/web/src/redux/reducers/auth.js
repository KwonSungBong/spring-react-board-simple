/**
 * Created by gwonseongbong on 8/10/16.
 */

const AUTH_LOAD =  'auth/AUTH_LOAD';
const AUTH_LOAD_SUCCESS = 'auth/AUTH_LOAD_SUCCESS';
const AUTH_LOAD_FAIL = 'auth/AUTH_LOAD_FAIL';

const AUTH_TOKEN = 'auth/AUTH_TOKEN';
const AUTH_TOKEN_SUCCESS = 'auth/AUTH_TOKEN_SUCCESS';
const AUTH_TOKEN_FAIL = 'auth/AUTH_TOKEN_FAIL';

const LOGIN = 'auth/LOGIN';
const LOGIN_SUCCESS = 'auth/LOGIN_SUCCESS';
const LOGIN_FAIL = 'auth/LOGIN_FAIL';

const LOGOUT = 'auth/LOGOUT';
const LOGOUT_SUCCESS = 'auth/LOGOUT_SUCCESS';
const LOGOUT_FAIL = 'auth/LOGOUT_FAIL';

const initialState = {
    loaded: false
}

export default function reducer(state = initialState, action = {}) {
    switch(action.type){
        case AUTH_LOAD:
            return {
                ...state,
                loading: true
            }
        case AUTH_LOAD_SUCCESS:
            if(action.result.email) {
                return {
                    ...state,
                    loading: false,
                    loaded: true,
                    user: action.result
                }
            }

            return {
                ...state,
                loading: false,
                loaded: false
            }
        case AUTH_LOAD_FAIL:
            return {
                ...state
            }
        case AUTH_TOKEN:
            return {
                ...state
            }
        case AUTH_TOKEN_SUCCESS:
            return {
                ...state,
                token: action.result.token
            }
        case LOGIN:
            return {
                ...state
            }
        case LOGIN_SUCCESS:
            return {
                ...state,
                loaded: true,
                user: action.result
            }
        case LOGIN_FAIL:
            return {
                ...state
            }
        case LOGOUT:
            return {
                ...state
            }
        case LOGOUT_SUCCESS:
        case LOGOUT_FAIL:
            return {
                ...state,
                user: null,
                token: null,
                loaded: false
            }
        default:
            return state;
    }
}

export function isLoaded(globalState) {
    return globalState.auth && globalState.auth.loaded;
}

export function load(){
    return {
        types: [AUTH_LOAD, AUTH_LOAD_SUCCESS, AUTH_LOAD_FAIL],
        promise: (client) => client.get('/auth/me')
    }
}

export function authToken(){
    return {
        types: [AUTH_TOKEN, AUTH_TOKEN_SUCCESS, AUTH_TOKEN_FAIL],
        promise: (client) => client.get('/auth')
    }
}

export function login(values, token){
    return {
        types: [LOGIN, LOGIN_SUCCESS,  LOGIN_FAIL],
        promise: (client) => client.post('/auth/login', {
            data: values,
            token: token,
            authorization: 'Basic ' + btoa(values.email + ':' + values.password)
        })
    }
}

export function logout(token){
    return {
        types: [LOGOUT, LOGOUT_SUCCESS, LOGOUT_FAIL],
        promise: (client) => client.post('/auth/logout', {
            token: token
        })
    }
}