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
			<Navbar inverse>
				<Navbar.Header>
					<Navbar.Brand>
						<a href="/">Board</a>
					</Navbar.Brand>
					<Navbar.Toggle />
				</Navbar.Header>
				<Navbar.Collapse>
					<Nav>
						<LinkContainer to="/post">
							<NavItem eventKey={1} href="#">포스트</NavItem>
						</LinkContainer>
						<LinkContainer to="/login">
							<NavItem eventKey={2} href="#">채팅</NavItem>
						</LinkContainer>
						<NavDropdown eventKey={3} title="Dropdown" id="basic-nav-dropdown">
							<MenuItem eventKey={3.1}>test1</MenuItem>
							<MenuItem eventKey={3.2}>test2</MenuItem>
							<MenuItem eventKey={3.3}>test3</MenuItem>
							<MenuItem divider />
							<MenuItem eventKey={3.3}>test4</MenuItem>
						</NavDropdown>
					</Nav>
					<Nav pullRight>
						<LinkContainer to="/login">
							<NavItem eventKey={1} href="#">로그인</NavItem>
						</LinkContainer>
						<LinkContainer to="/join">
							<NavItem eventKey={2} href="#">가입</NavItem>
						</LinkContainer>
					</Nav>
				</Navbar.Collapse>
			</Navbar>
		)
	}
}