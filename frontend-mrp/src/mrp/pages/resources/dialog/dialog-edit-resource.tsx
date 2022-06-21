import React, { useEffect, useState } from 'react';
import { Modal } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { MrpProgressBar } from '../../../components/progress-bar';
import { Resource } from '../../../model/resource';
import { getResource, saveResource } from '../../../data/data-providers';
import { MrpResourceEditForm } from './form-edit-resource';

export function MrpDialogEditResource() {
    const navigate = useNavigate();
    const { resourceId } = useParams();
    const handleClose = () => navigate("/mrp/list");
    const [ resource, setResource ] = useState<null | Resource>(null);

    const onSave = (toSave: Resource) => {
        saveResource(toSave).then(() => {
            handleClose();
        })
    };

    useEffect(() => {
        getResource(resourceId as string).then(res => {
            setResource(res);
        })
    }, [resourceId]); 

    const content: React.ReactNode = (resource === null) ? 
        <MrpProgressBar /> :
        <MrpResourceEditForm resource={resource} onSubmit={onSave} />

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Edit an existing resource</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {content}
            </Modal.Body>
        </Modal>
    );
}