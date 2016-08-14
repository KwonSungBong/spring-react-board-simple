/**
 * Created by kwonohbin on 2016. 7. 31..
 */
import React, {Component} from 'react';
import {Navbar, Nav, NavItem, NavDropdown, MenuItem} from 'react-bootstrap';
import { Link } from 'react-router';
import { LinkContainer } from 'react-router-bootstrap';

export default class Top extends Component {

	render() {
		return (
			<Navbar>
				<Navbar.Header>

				</Navbar.Header>
				<Navbar.Collapse>
					<Nav>
						<LinkContainer to="">
							<NavItem eventKey={1}>
								홈
							</NavItem>
						</LinkContainer>
						<NavDropdown eventKey={2} title="멀티플" id="etc">
							<LinkContainer to="/post">
								<MenuItem eventKey={2.1}>포스트</MenuItem>
							</LinkContainer>
							<LinkContainer to="/card">
								<MenuItem eventKey={2.2}>카드</MenuItem>
							</LinkContainer>
							<LinkContainer to="/chat">
								<MenuItem eventKey={2.3}>채팅</MenuItem>
							</LinkContainer>
							<LinkContainer to="/login">
								<MenuItem eventKey={2.4}>로그인</MenuItem>
							</LinkContainer>
						</NavDropdown>
					</Nav>
				</Navbar.Collapse>
			</Navbar>
		)
	}
}