import { LocalDate, LocalDateTime, Month } from '@js-joda/core';
import React, { useState, useEffect } from 'react';
import { Col, ProgressBar, Row, Table } from 'react-bootstrap';
import { useLocation, useSearchParams } from 'react-router-dom';
import { getResources } from './data-providers';
import { mapDates } from './date-utils';
import { ResourcesResponse } from './model/resources-response';
import { MrpResourceRow } from './resource-row';

interface Period {
    dateStart: LocalDate, 
    dateEnd: LocalDate
}

export function MrpHomeResources() {
    const [ dataLoading, setDataLoading ] = useState<boolean>(false);
    const [ response, setResponse ] = useState<null | ResourcesResponse>(null);
    const location = useLocation();
    const [ queryParams ] = useSearchParams();

    const getPeriod = (): Period => {
        let dateStart = LocalDate.now(); 
        let dateEnd = dateStart.plusDays(7);

        if (queryParams.has('startDate')) {
            dateStart = LocalDate.parse(queryParams.get('startDate') as string);
        }
        if (queryParams.has('endDate')) {
            dateEnd = LocalDate.parse(queryParams.get('endDate') as string);
        }

        return { dateStart, dateEnd };
    };

    useEffect(() => {
        // dummy data, should be provided externally
        const { dateStart, dateEnd } = getPeriod();

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