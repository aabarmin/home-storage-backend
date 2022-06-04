import React from 'react';
import { Modal } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

export function MrpDialogEditConsumption() {
    const { consignmentId, recordDate } = useParams();
    const navigate = useNavigate();
    const handleClose = () => navigate("/");

    

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Edit Consumption</Modal.Title>
            </Modal.Header>
            <Modal.Body>

            </Modal.Body>
        </Modal>
    );
}