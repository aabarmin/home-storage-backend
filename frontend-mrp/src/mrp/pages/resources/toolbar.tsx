import React from 'react';
import { Col, Dropdown, DropdownButton, Row } from 'react-bootstrap';

export function MrpResourcesListToolbar() {
    return (
        <Row style={{ paddingBottom: '10px' }}>
            <Col>
                <DropdownButton title="Create new">
                    <Dropdown.Item>Resource</Dropdown.Item>
                </DropdownButton>
            </Col>
        </Row>
    );
}