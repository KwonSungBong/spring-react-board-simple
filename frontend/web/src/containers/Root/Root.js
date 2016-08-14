/**
 * Created by kwonohbin on 2016. 7. 31..
 */
import React, {PropTypes, Component} from 'react';
import Helmet from 'react-helmet';
import Top from '../../components/Root/Top';

import config from '../../config';

export default class Root extends Component {
	static propTypes = {
		children: PropTypes.object.isRequired
	};

	render() {
		return (
			<div>
				<Helmet {...config.app} />
				<Top />
				{this.props.children}
			</div>
		)
	}
}