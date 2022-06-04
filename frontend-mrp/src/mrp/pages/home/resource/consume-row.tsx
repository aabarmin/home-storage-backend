import { LocalDate } from '@js-joda/core';
import React from 'react';
import { Consignment, getConsume } from '../../../model/consignment';
import { mapDates } from '../date-utils';
import { MrpEditableCell } from './editable-cell';

interface ConsumeRowProps {
    consignment: Consignment, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
};

export const MrpResourceConsignmentConsumeRow = (props: ConsumeRowProps) => {
    const daysEditable: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, (date: LocalDate) => {
        const key=`consumption-${props.consignment.id}-${date.toString()}`
        const value = getConsume(props.consignment, date).amount;
        return (<MrpEditableCell key={key} value={value} onClick={() => {}} />);
    });

    return (
        <tr>
            <td colSpan={2}>&nbsp;</td>
            <td colSpan={2} className='row-title-cell' style={{ borderRight: '2px solid black' }}>Consumption</td>
            {daysEditable}
        </tr>
    );
};