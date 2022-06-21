import React, { useEffect, useState } from 'react';
import { Col, ProgressBar, Row, Table } from 'react-bootstrap';
import { getResourcesList } from '../home/data-providers';
import { MrpResourcesListRow } from './list-row';
import { ResourceListResponse } from './model/resource-list-response';

export function MrpResourcesList() {

    const [dataLoading, setDataLoading] = useState<boolean>(false);
    const [response, setResponse] = useState<null | ResourceListResponse>(null);

    useEffect(() => {
        setDataLoading(true);
        getResourcesList().then(response => {
            setDataLoading(false);
            setResponse(response);
        });
    }, []); 

    if (response === null || dataLoading === true) {
        return (<ProgressBar animated now={100} />);
    }

    const resources: React.ReactNode[] = response.resources.map(r => {
        return (<MrpResourcesListRow 
                                    key={`resource-row-${r.resourceId}`}
                                    resource={r} />);
    });

    return (
        <Row>
            <Col>
                <Table bordered className='resources-list-table'>
                    <thead>
                        <tr>
                            <th colSpan={2}>
                                Resource
                            </th>
                            <th>
                                Leftover
                            </th>
                            <th>
                                Actions
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {resources}
                    </tbody>
                </Table>
            </Col>
        </Row>
    );
}