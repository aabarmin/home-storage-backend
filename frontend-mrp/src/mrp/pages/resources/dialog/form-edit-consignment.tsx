import React from 'react';
import { Button, Form } from 'react-bootstrap';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Consignment } from '../../../model/consignment';
import { ConsumptionUnit } from '../../../model/consumption-unit';

interface Props {
    consignment: Consignment, 
    consumptionUnits: ConsumptionUnit[], 
    onSubmit: (consignment: Consignment) => void
}

interface FormData {
    name: string, 
    consumptionUnit: string
}

export function MrpConsignmentEditForm(props: Props) {
    const { register, handleSubmit, formState: {errors} } = useForm<FormData>({
        defaultValues: {
            name: props.consignment.name, 
            consumptionUnit: props.consignment.unit.alias
        }
    });
    const onSubmit: SubmitHandler<FormData> = (data: FormData) => {
        // creating a new consignment
        const newConsignment: Consignment = {
            resourceId: props.consignment.resourceId, 
            consignmentId: props.consignment.consignmentId, 
            name: data.name, 
            unit: props.consumptionUnits.filter(u => u.alias === data.consumptionUnit)[0]
        }
        // pass is back
        props.onSubmit(newConsignment);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} noValidate validated={true}>
            <Form.Group>
                <Form.Label>Name</Form.Label>
                <Form.Control
                    {...register('name', {
                        required: true, 
                        minLength: 3, 
                        maxLength: 255
                    })}
                    placeholder='Name of the consignment'
                    type='text' />
                <Form.Control.Feedback>
                    {errors.name?.type === 'require' && 'Value is required'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.name?.type === 'minLength' && 'Value should be longer than 3 chars'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.name?.type === 'maxLength' && 'Value should be shorter than 255 chars'}
                </Form.Control.Feedback>
            </Form.Group>
            <Form.Group>
                <Form.Label>Consumption Unit</Form.Label>
                <Form.Select 
                    {...register('consumptionUnit', {
                        required: true
                    })}>
                    {props.consumptionUnits.map(unit => {
                        return (
                            <option 
                                    key={unit.alias}
                                    value={unit.alias}>
                                {unit.name}
                            </option>)
                    })}
                </Form.Select>
                <Form.Control.Feedback>
                    {errors.consumptionUnit?.type === 'required' && 'Values is required'}
                </Form.Control.Feedback>
            </Form.Group>
            <Button type='submit'>
                Save
            </Button>
        </Form>
    );
}