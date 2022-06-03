import { LocalDate } from '@js-joda/core';
import React from 'react';
import { Consignment } from '../../../model/consignment';
import { mapDates } from '../date-utils';

interface ConsignmentRowProps {
    consignment: Consignment, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
};

export const MrpResourceConsignmentRow = (props: ConsignmentRowProps) => {
    const daysEmpty: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, (date: LocalDate) => {
        const key = `consignment-${props.consignment.id}-empty-${date.toString()}`
        return (<td className='empty-cell' key={key}>&nbsp;</td>);
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