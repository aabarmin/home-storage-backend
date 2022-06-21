import { Col, DropdownButton, Row } from 'react-bootstrap';
import { Link } from 'react-router-dom';

export function MrpResourcesListToolbar() {
    return (
        <Row style={{ paddingBottom: '10px' }}>
            <Col>
                <DropdownButton title="Create new">
                    <Link to={'resources/new'} className="dropdown-item">Resource</Link>
                </DropdownButton>
            </Col>
        </Row>
    );
}