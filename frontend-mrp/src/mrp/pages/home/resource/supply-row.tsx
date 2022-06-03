import { LocalDate } from '@js-joda/core';
import React from 'react';
import { Consignment } from '../../../model/consignment';
import { mapDates } from '../date-utils';
import { MrpEditableCell } from './editable-cell';

interface SupplyRowProps {
    consignment: Consignment, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
};

export const MrpResourceConsignmentSupplyRow = (props: SupplyRowProps) => {
    const daysEditable: React.ReactNode[] = mapDates(props.dateStart, props.dateEnd, () => {
        return (<MrpEditableCell value={10} />);
    });

    return (
        <tr>
            <td colSpan={2}>&nbsp;</td>
            <td colSpan={2} className='row-title-cell' style={{ borderRight: '2px solid black' }}>Supply</td>
            {daysEditable}
        </tr>
    );
};