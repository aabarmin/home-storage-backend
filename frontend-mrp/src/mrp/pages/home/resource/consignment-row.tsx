import { LocalDate } from '@js-joda/core';
import React from 'react';
import { ConsignmentWithResources, getLeftover } from '../../../model/consignment';
import { mapDates } from '../date-utils';

interface ConsignmentRowProps {
    consignment: ConsignmentWithResources, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
};

export const MrpResourceConsignmentRow = (props: ConsignmentRowProps) => {
    const daysEmpty: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, (date: LocalDate) => {
        const key = `consignment-${props.consignment.consignmentId}-empty-${date.toString()}`
        const value = getLeftover(props.consignment, date);
        return (<td className='readonly-cell' key={key}>{value.amount}</td>);
    });

    return (
        <tr>
            <td>&nbsp;</td>
            <td colSpan={3} className='row-title-cell' style={{ borderRight: '2px solid black' }}>
                {props.consignment.name}
            </td>
            {daysEmpty}
        </tr>
    );
};