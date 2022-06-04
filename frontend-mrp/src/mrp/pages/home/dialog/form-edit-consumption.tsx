import { LocalDate } from '@js-joda/core';
import React from 'react';
import { Form, Button, FormGroup } from 'react-bootstrap';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Consignment } from '../../../model/consignment';
import { DayRecord } from '../../../model/day-record';

interface ComponentProps {
    consignment: Consignment,
    dayRecord: DayRecord, 
    date: LocalDate
}

interface FormData {
    date: string, 
    unit: string, 
    value: number
}

export function MrpEditConsumptionForm(props: ComponentProps) {
    const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
        defaultValues: {
            date: props.date.toString(), 
            unit: props.consignment.unit.name, 
            value: props.dayRecord.consumption.amount
        }
    });
    const onSubmit: SubmitHandler<FormData> = (data) => { console.log(data); };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} noValidate validated={true}>
            <Form.Group>
                <Form.Label>Date</Form.Label>
                <Form.Control 
                    {...register('date')}
                    disabled
                    type='date' 
                    placeholder='Date of record'  />
            </Form.Group>
            <Form.Group>
                <Form.Label>Unit</Form.Label>
                <Form.Control 
                    {...register('unit')}
                    disabled
                    type='text' 
                    placeholder='Measurement unit' />
            </Form.Group>
            <Form.Group>
                <Form.Label>Consumption</Form.Label>
                <Form.Control 
                    {...register('value', {
                        required: true, 
                        pattern: /^\d+$/
                    })}
                    type='text' 
                    placeholder='Consumption value' />
                <Form.Control.Feedback>
                    {errors.value?.type == 'pattern' && 'Value should be a number'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.value?.type == 'required' && 'Value should be provided'}
                </Form.Control.Feedback>
            </Form.Group>
            <Button type='submit'>
                Save
            </Button>
        </Form>
    );
}