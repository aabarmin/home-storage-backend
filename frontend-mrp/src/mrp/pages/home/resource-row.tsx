import { LocalDate } from '@js-joda/core';
import { useState } from 'react';
import { Resource } from '../../model/resource';
import { MrpResourceConsignmentRow } from './resource/consignment-row';
import { MrpResourceConsignmentConsumeRow } from './resource/consume-row';
import { MrpResourceRowFirst } from './resource/first-row';
import { MrpResourceConsignmentSupplyRow } from './resource/supply-row';

interface ComponentProps {
    resource: Resource, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
}

export function MrpResourceRow(props: ComponentProps) {
    const [opened, setOpened] = useState(false);

    const addition = !opened ? [] : props.resource.consignments.map(c => {
        return (
            <>
                <MrpResourceConsignmentRow consignment={c}
                                           dateStart={props.dateStart}
                                           dateEnd={props.dateEnd} />
                <MrpResourceConsignmentConsumeRow consignment={c}
                                                 dateStart={props.dateStart}
                                                 dateEnd={props.dateEnd} />
                <MrpResourceConsignmentSupplyRow consignment={c}
                                                  dateStart={props.dateStart}
                                                  dateEnd={props.dateEnd} />                                                 
            </>
        );
    });

    return (
        <>
            <MrpResourceRowFirst resource={props.resource} 
                                 opened={opened}
                                 dateStart={props.dateStart}
                                 dateEnd={props.dateEnd}
                                 toggleOpen={() => setOpened(!opened)} />
            {addition}
        </>
    ); 
}