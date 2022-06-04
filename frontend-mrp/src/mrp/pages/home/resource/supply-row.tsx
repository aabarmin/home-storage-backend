import { LocalDate } from '@js-joda/core';
import React from 'react';
import { ConsignmentWithResources, getSupply } from '../../../model/consignment';
import { mapDates } from '../date-utils';
import { MrpEditableLinkCell } from './editable-link-cell';

interface SupplyRowProps {
    consignment: ConsignmentWithResources, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
};

export const MrpResourceConsignmentSupplyRow = (props: SupplyRowProps) => {
    const daysEditable: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, (date: LocalDate) => {
        const key=`supply-${props.consignment.consignmentId}-${date.toString()}`
        const value = getSupply(props.consignment, date).amount;
        const link = `consignments/${props.consignment.consignmentId}/records/${date.toString()}/supplies`;
        return (<MrpEditableLinkCell key={key} value={value} link={link} />);
    });

    return (
        <tr>
            <td colSpan={2}>&nbsp;</td>
            <td colSpan={2} className='row-title-cell' style={{ borderRight: '2px solid black' }}>Supply</td>
            {daysEditable}
        </tr>
    );
};