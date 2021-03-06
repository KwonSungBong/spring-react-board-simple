/**
 * Created by 권성봉 on 2016. 8. 1..
 */
const FIND_ALL = "post/FIND";
const FIND_ALL_SUCCESS = "post/FIND_ALL_SUCCESS";
const FIND_ALL_FAIL = "post/FIND_ALL_FAIL";

const FIND_LIST = "post/FIND_LIST";
const FIND_LIST_SUCCESS = "post/FIND_LIST_SUCCESS";
const FIND_LIST_FAIL = "post/FIND_LIST_FAIL";

const MORE_LIST = "post/MORE_LIST";
const MORE_LIST_SUCCESS = "post/MORE_LIST_SUCCESS";
const MORE_LIST_FAIL = "post/MORE_LIST_FAIL";

const FIND_ONE = "post/FIND_ONE";
const FIND_ONE_SUCCESS = "post/FIND_ONE_SUCCESS";
const FIND_ONE_FAIL = "post/FIND_ONE_FAIL";

const RESET_ONE = "post/RESET_ONE";

const CREATE = "post/CREATE";
const CREATE_SUCCESS = "post/CREATE_SUCCESS";
const CREATE_FAIL = "post/CREATE_FAIL";

const UPDATE = "post/UPDATE";
const UPDATE_SUCCESS = "post/UPDATE_SUCCESS";
const UPDATE_FAIL = "post/UPDATE_FAIL";

const REMOVE = "post/REMOVE";
const REMOVE_SUCCESS = "post/REMOVE_SUCCESS";
const REMOVE_FAIL = "post/REMOVE_FAIL";

const SHOW_DETAIL_MODAL = "post/SHOW_DETAIL_MODAL";
const HIDE_DETAIL_MODAL = "post/HIDE_DETAIL_MODAL";

const initialState = {
    loading: false,
    postList: {},
    post: {},
    isDetailModal: false,
    isFormModal: true
};

export default function reducer(state = initialState, action = {}) {
    switch (action.type){
        case FIND_ALL:
            return {
                ...state,
                loading: true
            };
        case FIND_ALL_SUCCESS:
            return {
                ...state,
                postList: action.result,
                loading: false
            };
        case FIND_ALL_FAIL:
            return {
                ...state,
                loading: false
            };
        case FIND_LIST:
            return {
                ...state,
                loading: true
            };
        case FIND_LIST_SUCCESS:
            return {
                ...state,
                postList: action.result,
                loading: false
            };
        case FIND_LIST_FAIL:
            return {
                ...state,
                loading: false
            };
        case MORE_LIST:
            return {
                ...state,
                loading: true
            };
        case MORE_LIST_SUCCESS:
            return {
                ...state,
                postList: Object.assign({}, action.result, {
                    content: [...state.postList.content, ...action.result.content]
                }),
                loading: false
            };
        case MORE_LIST_FAIL:
            return {
                ...state,
                loading: false
            };
        case FIND_ONE:
            return {
                ...state,
                loading: true
            }
        case FIND_ONE_SUCCESS:
            return {
                ...state,
                post: action.result,
                loading: false
            };
        case FIND_ONE_FAIL:
            return {
                ...state,
                loading: false
            };
        case RESET_ONE:
            return {
                ...state,
                post: {}
            };
        case CREATE:
            return {
                ...state,
                loading: true
            };
        case CREATE_SUCCESS:
            return {
                ...state,
                loading: true
            };
        case CREATE_FAIL:
            return {
                ...state,
                loading: false
            };
        case UPDATE:
            return {
                ...state,
                loading: true
            };
        case UPDATE_SUCCESS:
            return {
                ...state,
                post: action.post,
                loading: false
            };
        case UPDATE_FAIL:
            return {
                ...state,
                loading: false
            };
        case REMOVE:
            return {
                ...state,
                loading: true
            };
        case REMOVE_SUCCESS:
            return {
                ...state,
                postList: Object.assign({}, state.postList, {
                    content: state.postList.content.filter(post =>
                        post.idx !== action.idx
                    )
                }),
                loading: false
            };
        case REMOVE_FAIL:
            return {
                ...state,
                loading: false
            };
        case SHOW_DETAIL_MODAL:
            return {
                ...state,
                isDetailModal: true
            };

        case HIDE_DETAIL_MODAL:
            return {
                ...state,
                isDetailModal: false
            };
        default:
            return state;
    }
}

export function findAll(){
    return {
        types: [FIND_ALL, FIND_ALL_SUCCESS, FIND_ALL_FAIL],
        promise: (client) => client.get('/post/findAll')
    }
}

export function findList(page = 0){
    return {
        types: [FIND_LIST, FIND_LIST_SUCCESS, FIND_LIST_FAIL],
        promise: (client) => client.get('/post?page=' + page)
    }
}

export function moreList(morePage){
    return {
        types: [MORE_LIST, MORE_LIST_SUCCESS, MORE_LIST_FAIL],
        promise: (client) => client.get('/post?page=' + morePage)
    }
}

export function findOne(idx){
    return {
        types: [FIND_ONE, FIND_ONE_SUCCESS, FIND_ONE_FAIL],
        promise: (client) => client.get('/post/' + idx)
    }
}

export function resetOne(){
    return {
        type: RESET_ONE
    }
}

export function create(post){
    return {
        types: [CREATE, CREATE_SUCCESS, CREATE_FAIL],
        promise: (client) => client.post('/post', {
            data: post
        })
    }
}

export function update(post){
    return {
        types: [UPDATE, UPDATE_SUCCESS, UPDATE_FAIL],
        promise: (client) => client.patch('/post', {
            data: post
        }),
        post: post
    }
}

export function remove(idx){
    return {
        types: [REMOVE, REMOVE_SUCCESS, REMOVE_FAIL],
        promise: (client) => client.del('/post/' + idx),
        idx: idx
    }
}

export function showDetailModal(){
    return {
        type: SHOW_DETAIL_MODAL
    }
}

export function hideDetailModal(){
    return {
        type: HIDE_DETAIL_MODAL
    }
}