import React from 'react';
import { Link } from 'react-router-dom';
import { Navbar, Container, Nav, NavItem } from 'react-bootstrap';

export function MrpNavigation() {
    return (
        <Navbar>
            <Container fluid>
                <Link to="/mrp" className="navbar-brand">MRP Application</Link>    

                <Nav className='me-auto'>
                    <NavItem>
                        <Link to="/mrp" className="nav-link">
                            Home
                        </Link>
                    </NavItem>
                    <NavItem>
                        <Link to="/mrp/list" className="nav-link">
                            Resources
                        </Link>
                    </NavItem>
                </Nav>
            </Container>
        </Navbar>
    );
}