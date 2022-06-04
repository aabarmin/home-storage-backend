import { LocalDate } from '@js-joda/core';
import React, { useEffect, useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { Row, Col, ButtonGroup } from 'react-bootstrap';
import { useForm, useWatch } from 'react-hook-form';
import { useSearchParams } from 'react-router-dom';

interface FormData {
    startDate: string, 
    endDate: string
}

export function MrpHomeDateSelector() {
    const { register, getValues, control, setValue } = useForm<FormData>({
        defaultValues: {
            startDate: LocalDate.now().toString(), 
            endDate: LocalDate.now().plusWeeks(1).toString()
        }
    });
    const [ calendarType, setCalendarType ] = useState<'week' | '2weeks' | 'month'>('week');
    const [ maxEndDate, setMaxEndDate ] = useState<LocalDate>(LocalDate.now().plusWeeks(1));
    const [ minEndDate, setMinEndDate ] = useState<LocalDate>(LocalDate.now());
    const [ queryParams, setQueryParams ] = useSearchParams();

    const syncDates = (startDate: LocalDate, endDate: LocalDate, type: 'week' | '2weeks' | 'month') => {
        // calculate max possible end date
        let endDateMax = startDate.plusWeeks(1);
        if (type === '2weeks') {
            endDateMax = startDate.plusWeeks(2);
        } else if (type === 'month') {
            endDateMax = startDate.plusMonths(1);
        }
        if (endDate.isAfter(endDateMax)) {
            setValue('endDate', endDateMax.toString());
        }

        // set max/min end dates
        setMaxEndDate(endDateMax);
        setMinEndDate(startDate);

        // set value in the location
        setQueryParams({
            startDate: startDate.toString(), 
            endDate: endDate.toString()
        });
    };

    const select = (value: 'week' | '2weeks' | 'month') => {
        setCalendarType(value);
        syncDates(
            LocalDate.parse(getValues('startDate')), 
            LocalDate.parse(getValues('endDate')), 
            value
        )
    }

    const startDateWatch = useWatch({name: 'startDate', control: control});
    const endDateWatch = useWatch({name: 'endDate', control: control});
    useEffect(() => {
        syncDates(
            LocalDate.parse(getValues('startDate')), 
            LocalDate.parse(getValues('endDate')), 
            calendarType
        )
    }, [startDateWatch, endDateWatch]);

    return (
        <Row style={{paddingBottom: '10px'}}>
            <Col xs={1}>
                Period
            </Col>
            <Col xs={3}>
                <ButtonGroup>
                    <Button 
                        onClick={() => select('week')}
                        variant={calendarType === 'week' ? 'primary' : 'secondary'}>
                            Week
                    </Button>
                    <Button 
                        onClick={() => select('2weeks')}
                        variant={calendarType === '2weeks' ? 'primary' : 'secondary'}>
                            2 Weeks
                    </Button>
                    <Button 
                        onClick={() => select('month')}
                        variant={calendarType === 'month' ? 'primary' : 'secondary'}>
                            Month
                    </Button>
                </ButtonGroup>
            </Col>
            <Col xs={4}>
                <Form>
                    <Row>
                        <Col>
                            <Form.Control 
                                {...register('startDate')}
                                placeholder='Start date' 
                                type='date' />
                        </Col>
                        <Col>
                            <Form.Control 
                                {...register('endDate')}
                                min={minEndDate.toString()}
                                max={maxEndDate.toString()}
                                placeholder='End date' 
                                type='date' />
                        </Col>
                    </Row>
                </Form>
            </Col>
        </Row>
    );
}