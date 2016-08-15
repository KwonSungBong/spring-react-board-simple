import React from 'react';
import {IndexRoute, Route} from 'react-router';
import { isLoaded as isAuthLoaded, load as loadAuth} from 'redux/reducers/auth';

import {
	Root,
	Home,
	Login,
	Join,
	Post,
	PostList,
	PostForm,
	PostDetail
} from './containers';

export default (store) => {
	//store.dispatch(loadAuth());

	// onEnter={requireLogin}
	const requireLogin = (nextState, replace, cb) => {
		function checkAuth() {
			const { auth: { user }} = store.getState();
			if (!user) {
				// oops, not logged in, so can't be here!
				replace({
					pathname: '/login',
					state: {nextPathname: nextState.location.pathname}
				});
			}
			cb();
		}

		if (!isAuthLoaded(store.getState())) {
			store.dispatch(loadAuth()).then(checkAuth).catch(e => {
				replace({
					pathname: '/login',
					state: {nextPathname: nextState.location.pathname}
				});

				cb();
			});
		} else {
			checkAuth();
		}
	};

	return (
		<Route path="/" component={Root}>
			{ /* Home (main) route */ }
			<IndexRoute component={Home}/>
			<Route path="login" component={Login} />
			<Route path="join" component={Join} />
			<Route path="post" component={Post}>
				<IndexRoute component={PostList} />
				<Route path="form" component = {PostForm} />
				<Route path="form/:idx" component = {PostForm} />
				<Route path="detail/:idx" component = {PostDetail} />
			</Route>
		</Route>
	);
};
