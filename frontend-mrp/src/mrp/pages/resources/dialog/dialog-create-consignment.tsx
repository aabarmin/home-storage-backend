import { useEffect, useState } from 'react';
import { Modal } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { MrpProgressBar } from '../../../components/progress-bar';
import { Consignments, ConsumptionUnits } from '../../../data/data-providers';
import { Consignment } from '../../../model/consignment';
import { ConsumptionUnit } from '../../../model/consumption-unit';
import { MrpConsignmentEditForm } from './form-edit-consignment';

interface FormData {
    consignment: Consignment, 
    consumptionUnits: ConsumptionUnit[]
}

export function MrpDialogCreateConsignment() {
    const { resourceId } = useParams();
    const navigate = useNavigate();
    const handleClose = () => navigate("/mrp/list");
    const onSave = (toSave: Consignment) => {
        Consignments.saveConsignment(toSave).then(() => {
            handleClose();
        });
    };    

    useEffect(() => {
        Promise.all([
            Consignments.createConsignment(resourceId as string), 
            ConsumptionUnits.getConsumptionUnits()
        ]).then(([consignment, units]) => {
            setFormData({
                consignment: consignment, 
                consumptionUnits: units
            })
        });
    }, [resourceId]);

    const [ formData, setFormData ] = useState<null | FormData>(null);
    const content = formData === null ? 
        <MrpProgressBar /> : 
        <MrpConsignmentEditForm
                onSubmit={onSave}
                consumptionUnits={formData.consumptionUnits}
                consignment={formData.consignment} />


    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Create a new consignment</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {content}
            </Modal.Body>
        </Modal>
    );
}