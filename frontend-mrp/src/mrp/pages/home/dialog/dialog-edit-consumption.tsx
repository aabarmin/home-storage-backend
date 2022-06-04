import { LocalDate } from '@js-joda/core';
import React, { useEffect, useState } from 'react';
import { ProgressBar } from 'react-bootstrap';
import { Modal } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { getDayRecord } from '../data-providers';
import { DayRecordResponse } from '../model/day-record-response';
import { MrpEditConsumptionForm } from './form-edit-consumption';

export function MrpDialogEditConsumption() {
    const { consignmentId, recordDate } = useParams();
    const navigate = useNavigate();
    const handleClose = () => navigate("/");

    const [ dataLoading, setDataLoading ] = useState<boolean>(false);
    const [ response, setResponse ] = useState<DayRecordResponse | null>(null);

    useEffect(() => {
        setDataLoading(true); 
        getDayRecord(consignmentId as string, LocalDate.parse(recordDate as string)).then(dayResponse => {
            setDataLoading(false); 
            setResponse(dayResponse); 
        });
    }, []); 

    const content = (dataLoading || response === null) ? <ProgressBar animated now={100} /> : 
            <MrpEditConsumptionForm 
                    date={(response as DayRecordResponse).date}
                    consignment={(response as DayRecordResponse).consignment}
                    dayRecord={(response as DayRecordResponse).record} />;

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Edit Consumption</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {content}
            </Modal.Body>
        </Modal>
    );
}