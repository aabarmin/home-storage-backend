import { LocalDate, Month } from '@js-joda/core';
import React, { useState, useEffect } from 'react';
import { Col, ProgressBar, Row, Table } from 'react-bootstrap';
import { useLocation } from 'react-router-dom';
import { getResources } from './data-providers';
import { mapDates } from './date-utils';
import { ResourcesResponse } from './model/resources-response';
import { MrpResourceRow } from './resource-row';

export function MrpHomeResources() {
    const [ dataLoading, setDataLoading ] = useState<boolean>(false);
    const [ response, setResponse ] = useState<null | ResourcesResponse>(null);
    const location = useLocation();

    useEffect(() => {
        // dummy data, should be provided externally
        const dateStart = LocalDate.of(2020, Month.APRIL, 1);
        const dateEnd = LocalDate.of(2020, Month.APRIL, 30);

        setDataLoading(true);
        getResources(dateStart, dateEnd).then((response: ResourcesResponse) => {
            setDataLoading(false);
            setResponse(response);
        });
    }, [location]);

    if (response === null || dataLoading === true) {
        return (
            <ProgressBar animated now={100} />
        );
    }

    const dates = mapDates(response.dateStart, response.dateEnd, (date: LocalDate) => {
        const dayOfWeek = date.dayOfWeek().name().substring(0, 3);
        const day = date.dayOfMonth();
        const label = `${dayOfWeek} ${day}`;
        return (<td key={label}>{label}</td>);
    });
    
    const resources = response.resources.map(r => {
        return (<MrpResourceRow resource={r} 
                                dateStart={response.dateStart}
                                dateEnd={response.dateEnd}
                                key={`resource-${r.resourceId}`} />);
    }); 

    return (
        <Row>
            <Col>
                <div className='table-responsive'>
                    <Table bordered className='resources-table'>
                        <thead>
                            <tr>
                                <th style={{ width: '48px' }}>
                                    &nbsp;
                                </th>
                                <th style={{ width: '200px' }} colSpan={3} className='row-title-cell'>
                                    Resources
                                </th>
                                {dates}
                            </tr>
                        </thead>
                        <tbody key={`resource-body`}>
                            {resources}
                        </tbody>
                    </Table>
                </div>
            </Col>
        </Row>
    );
}