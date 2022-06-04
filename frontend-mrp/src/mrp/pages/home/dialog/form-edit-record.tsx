import { LocalDate } from '@js-joda/core';
import { Form, Button } from 'react-bootstrap';
import { SubmitHandler, useForm } from 'react-hook-form';

interface ComponentProps {
    date: LocalDate, 
    unit: string, 
    value: number, 
    onSubmit: (value: number) => void
}

interface FormData {
    date: string, 
    unit: string, 
    value: number
}

export function MrpEditDayRecordForm(props: ComponentProps) {
    const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
        defaultValues: {
            date: props.date.toString(), 
            unit: props.unit, 
            value: props.value
        }
    });
    const onSubmit: SubmitHandler<FormData> = (data: FormData) => { 
        props.onSubmit(data.value);
    };

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
                        min: 0, 
                        pattern: /^\d+$/
                    })}
                    type='text' 
                    placeholder='Consumption value' />
                <Form.Control.Feedback>
                    {errors.value?.type === 'pattern' && 'Value should be a number'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.value?.type === 'required' && 'Value should be provided'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.value?.type === 'min' && 'Value should be positive'}
                </Form.Control.Feedback>
            </Form.Group>
            <Button type='submit'>
                Save
            </Button>
        </Form>
    );
}