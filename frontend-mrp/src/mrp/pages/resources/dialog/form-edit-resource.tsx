import React from 'react';
import { Button, Form } from 'react-bootstrap';
import { SubmitHandler, useForm } from 'react-hook-form';
import { Resource } from '../../../model/resource';

interface ComponentProps {
    resource: Resource, 
    onSubmit: (resource: Resource) => void
}

interface FormData {
    name: string
}

export function MrpResourceEditForm(props: ComponentProps) {
    const { register, handleSubmit, formState: {errors} } = useForm<FormData>({
        defaultValues: {
            name: props.resource.name
        }
    });

    const onSubmit: SubmitHandler<FormData> = (data: FormData) => {
        // create a new instance of the resource with updated value
        const r: Resource = Object.assign(
            props.resource, 
            {
                name: data.name
            }
        );
        // submit
        props.onSubmit(r);
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
                    type='text'
                    placeholder='Name of resource' />
                <Form.Control.Feedback>
                    {errors.name?.type === 'maxLength' && 'Value should be shorter than 255 chars'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.name?.type === 'minLength' && 'Value should be longer than 3 chars'}
                </Form.Control.Feedback>
                <Form.Control.Feedback>
                    {errors.name?.type === 'required' && 'Value is required'}
                </Form.Control.Feedback>
            </Form.Group>
            <Button type='submit'>
                Save
            </Button>
        </Form>
    );
}