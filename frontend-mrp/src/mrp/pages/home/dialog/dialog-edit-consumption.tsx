import { LocalDate } from '@js-joda/core';
import { useEffect, useState } from 'react';
import { ProgressBar } from 'react-bootstrap';
import { Modal } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';
import { Amount } from '../../../model/amount';
import { getDayRecord, patchDayRecord } from '../data-providers';
import { DayRecordResponse } from '../model/day-record-response';
import { MrpEditDayRecordForm } from './form-edit-record';

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
    }, [consignmentId, recordDate]); 

    const onSubmit = (value: number) => {
        setDataLoading(true);
        const record = (response as DayRecordResponse).record;
        record.consumption = Amount.of(value, (response as DayRecordResponse).consignment.unit);

        patchDayRecord(record).then(() => {
            handleClose();
        });
    };

    const content = (dataLoading || response === null) ? <ProgressBar animated now={100} /> : 
            <MrpEditDayRecordForm 
                    onSubmit={onSubmit}
                    date={(response as DayRecordResponse).date}
                    unit={(response as DayRecordResponse).consignment.unit.name}
                    value={(response as DayRecordResponse).record.consumption.amount} />;

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