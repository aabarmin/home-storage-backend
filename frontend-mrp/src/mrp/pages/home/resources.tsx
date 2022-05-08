import { LocalDate, Month } from '@js-joda/core';
import React from 'react';
import { Col, ProgressBar, Row, Table } from 'react-bootstrap';
import { PlusCircle } from 'react-bootstrap-icons';
import { getRecords } from './data-providers';
import { Response } from './model/response';
import { MrpResourceRow } from './resource-row';

interface ComponentProps {}

interface ComponentState {
    dataLoading: boolean;
    response: Response | null; 
}

export class MrpHomeResources extends React.Component<ComponentProps, ComponentState> {
    constructor(props: any) {
        super(props);

        this.state = {
            dataLoading: false, 
            response: null
        };
    }

    generateDates = (response: Response): React.ReactNode[] => {
        const result: React.ReactNode[] = [];
        let currentDate = response.dateStart;
        // this implementation looks a little weird but it works. 
        // maybe I'll fix it next time. 
        while (currentDate.isBefore(response.dateEnd) || currentDate.isEqual(response.dateEnd)) {
            const label = String(currentDate.dayOfMonth()).padStart(2, "0") + ". " + 
                    String(currentDate.monthValue()).padStart(2, "0");
            result.push((<th key={label}>{label}</th>));
            currentDate = currentDate.plusDays(1);
        }
        return result;
    };

    generateResources = (response: Response): React.ReactNode[] => {
        return response.resources.map(r => {
            return (<MrpResourceRow resource={r} key={r.id} />);
        }); 
    };

    componentDidMount() {
        const startLoading = () => {
            this.setState((state: ComponentState) => {
                state.dataLoading = true; 
                state.response = null; 
                return state; 
            }); 
        }

        const endLoading = (response: Response) => {
            this.setState((state: ComponentState) => {
                state.dataLoading = false; 
                state.response = response;
                return state; 
            }); 
        };

        // dummy data
        const dateStart = LocalDate.of(2020, Month.APRIL, 1);
        const dateEnd = LocalDate.of(2020, Month.APRIL, 30);

        // start loading records
        startLoading(); 
        getRecords(dateStart, dateEnd).then((response: Response) => {
            endLoading(response);  
        });
    }

    render(): React.ReactNode {
        if (this.state.dataLoading || this.state.response == null) {
            return (
                <ProgressBar animated now={100} />
            ); 
        }

        const dates = this.generateDates(this.state.response as Response);
        const resources = this.generateResources(this.state.response as Response); 

        return (
            <Row>
                <Col>
                    <Table bordered>
                        <thead>
                            <tr>
                                <th style={{ width: '48px' }}>
                                    &nbsp;
                                </th>
                                <th style={{ width: '200px' }} colSpan={3}>
                                    Resources
                                </th>
                                <th style={{ width: '48px' }}>
                                    <button className='btn'>
                                        <PlusCircle />
                                    </button>
                                </th>
                                {dates}
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
}