import { LocalDate } from '@js-joda/core';
import React from 'react';
import { ConsignmentWithResources, getConsume } from '../../../model/consignment';
import { mapDates } from '../../../utils/date-utils';
import { MrpEditableLinkCell } from './editable-link-cell';

interface ConsumeRowProps {
    consignment: ConsignmentWithResources, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
};

export const MrpResourceConsignmentConsumeRow = (props: ConsumeRowProps) => {
    const daysEditable: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, (date: LocalDate) => {
        const key=`consumption-${props.consignment.consignmentId}-${date.toString()}`
        const value = getConsume(props.consignment, date).amount;
        const link = `consignments/${props.consignment.consignmentId}/records/${date.toString()}/consumptions`;
        return (<MrpEditableLinkCell key={key} value={value} link={link} />);
    });

    return (
        <tr>
            <td colSpan={2}>&nbsp;</td>
            <td colSpan={2} className='row-title-cell' style={{ borderRight: '2px solid black' }}>Consumption</td>
            {daysEditable}
        </tr>
    );
};