/**
 * 리듀서 전체 모음
 * 리듀서 파일 추가 시 마다 여기에 추가를 해주어야 한다.
 * Created by kwonohbin on 2016. 7. 31..
 */
import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import {reducer as form} from 'redux-form';
import {reducer as reduxAsyncConnect} from 'redux-connect';

import auth from './auth';
import post from './post';

import {normalizeSubject} from '../../helpers/normalize';

export default combineReducers({
	routing: routerReducer,
	reduxAsyncConnect,
	form:form.normalize({
		HistoryForm: {
			subject:normalizeSubject
		}
	}),
	auth,
	post
});