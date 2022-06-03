import { LocalDate } from '@js-joda/core';
import React from 'react';
import { ArrowsCollapse, ArrowsExpand, PlusCircle } from 'react-bootstrap-icons';
import { getLeftovers, Resource } from '../../../model/resource';
import { mapDates } from '../date-utils';

const getResourceLeftovers = (resource: Resource, dateStart: LocalDate, dateEnd: LocalDate): React.ReactNode[] => {
    return mapDates(dateStart, dateEnd, (currentDate: LocalDate) => {
        const amounts: string[] = getLeftovers(resource, currentDate).map(amount => {
            return `${amount.amount}`;
        })
        return (<td className='data-cell'>{amounts.join(', ')}</td>);
    });
};

interface FirstRowProps {
    resource: Resource, 
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
        <tr>
            <td className='expander-cell'>
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