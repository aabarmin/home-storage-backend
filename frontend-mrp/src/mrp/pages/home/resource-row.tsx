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
                <MrpResourceConsignmentRow key={`resource-consignment-${c.id}`}
                                           consignment={c}
                                           dateStart={props.dateStart}
                                           dateEnd={props.dateEnd} />
                {/* <MrpResourceConsignmentConsumeRow consignment={c}
                                                 dateStart={props.dateStart}
                                                 dateEnd={props.dateEnd} />
                <MrpResourceConsignmentSupplyRow consignment={c}
                                                  dateStart={props.dateStart}
                                                  dateEnd={props.dateEnd} />                                                  */}
            </>
        );
    });

    const mainElement = <MrpResourceRowFirst    key={`resource-row-first-${props.resource.id}`}
                                                resource={props.resource} 
                                                opened={opened}
                                                dateStart={props.dateStart}
                                                dateEnd={props.dateEnd}
                                                toggleOpen={() => setOpened(!opened)} />;

    const items: React.ReactNode[] = [mainElement];                                                

    if (opened) {
        const children = props.resource.consignments.map(c => {
            return ([
                <MrpResourceConsignmentRow  key={`resource-consignment-${c.id}`}
                                            consignment={c}
                                            dateStart={props.dateStart}
                                            dateEnd={props.dateEnd} />, 
                <MrpResourceConsignmentConsumeRow 
                                            key={`resource-consumption-${c.id}`}
                                            consignment={c}
                                            dateStart={props.dateStart}
                                            dateEnd={props.dateEnd} />,
                <MrpResourceConsignmentSupplyRow 
                                            key={`resource-supply-${c.id}`}
                                            consignment={c}
                                            dateStart={props.dateStart}
                                            dateEnd={props.dateEnd} />                                            
            ]);
        })
        children.forEach(item => items.push(item));
    }
    return (
        <>
            {items}
        </>
    );
}