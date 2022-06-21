import React from 'react';
import { Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { Resources } from '../../../data/data-providers';
import { Resource } from '../../../model/resource';
import { MrpResourceEditForm } from './form-edit-resource';

export function MrpDialogCreateResource() {
    const navigate = useNavigate();
    const handleClose = () => navigate("/mrp/list");
    const resource: Resource = {
        name: '', 
        resourceId: ''
    };
    const onSave = (toSave: Resource) => {
        Resources.saveResource(toSave).then(() => {
            handleClose();
        })
    };

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Create a new resource</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <MrpResourceEditForm 
                                onSubmit={onSave}
                                resource={resource} />
            </Modal.Body>
        </Modal>
    );
}