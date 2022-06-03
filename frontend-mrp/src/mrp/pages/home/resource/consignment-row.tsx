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
    const daysEmpty: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, () => {
        return (<td className='empty-cell'>&nbsp;</td>);
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