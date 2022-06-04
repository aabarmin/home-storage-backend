import { LocalDate } from '@js-joda/core';
import React from 'react';
import { ArrowsCollapse, ArrowsExpand } from 'react-bootstrap-icons';
import { getLeftovers, ResourceWithConsignments } from '../../../model/resource';
import { mapDates } from '../date-utils';

const getResourceLeftovers = (resource: ResourceWithConsignments, dateStart: LocalDate, dateEnd: LocalDate): React.ReactNode[] => {
    return mapDates(dateStart, dateEnd, (currentDate: LocalDate) => {
        const amounts: string[] = getLeftovers(resource, currentDate).map(amount => {
            return `${amount.amount}`;
        })
        const key = `resource-${resource.resourceId}-leftover-${currentDate.toString()}`
        return (<td className='readonly-cell' key={key}>{amounts.join(', ')}</td>);
    });
};

interface FirstRowProps {
    resource: ResourceWithConsignments, 
    dateStart: LocalDate, 
    dateEnd: LocalDate, 
    opened: boolean, 
    toggleOpen: () => void
}

export const MrpResourceRowFirst = (props: FirstRowProps) => {
    const expander = props.opened ? <ArrowsCollapse /> : <ArrowsExpand />
    const resourceLeftovers: React.ReactNode[] = getResourceLeftovers(
        props.resource, 
        props.dateStart, 
        props.dateEnd
    ); 

    return (
        <tr key={`resource-${props.resource.resourceId}-name`}>
            <td className='expander-cell' key={`resource-${props.resource.resourceId}-expander`}>
                <button className='btn btn-sm' onClick={props.toggleOpen}>
                    {expander}
                </button>
            </td>
            <td colSpan={3} className='row-title-cell' style={{ borderRight: '2px solid black' }}>
                {props.resource.name}
            </td>
            {resourceLeftovers}
        </tr>
    );
}