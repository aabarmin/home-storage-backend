import React, { useState } from 'react';
import { Dropdown, DropdownButton } from 'react-bootstrap';
import { ArrowsCollapse, ArrowsExpand } from 'react-bootstrap-icons';
import { Link } from 'react-router-dom';
import { ConsignmentWithLeftovers } from '../../model/consignment';
import { ResourceWithLeftovers } from '../../model/resource';

interface ComponentProps {
    resource: ResourceWithLeftovers
}

interface MainRowProps extends ComponentProps {
    opened: boolean, 
    toggleOpened: () => void
}

function MrpResourcesListMainRow(props: MainRowProps) {
    const expander: React.ReactNode = props.opened ? 
        <ArrowsExpand /> :
        <ArrowsCollapse />;

    return (
        <tr>
            <td className='expander-cell'>
                <button className='btn btn-sm' onClick={props.toggleOpened}>
                    {expander}
                </button>
            </td>
            <td className='data-cell'>
                {props.resource.name}
            </td>
            <td>&nbsp;</td>
            <td>
                <DropdownButton title='Actions'>
                    <Link to={`resources/${props.resource.resourceId}`} className='dropdown-item'>Edit</Link>
                </DropdownButton>
            </td>
        </tr>
    );
}

interface ConsignmentProps {
    consignment: ConsignmentWithLeftovers
}

function MrpResourcesListConsignmentRow(props: ConsignmentProps) {
    const amount = `${props.consignment.leftover.amount} ${props.consignment.leftover.unit.nameShort}`

    return (
        <tr>
            <td>&nbsp;</td>
            <td className='data-cell'>
                {props.consignment.name}
            </td>
            <td className='data-cell'>
                {amount}
            </td>
            <td>
                <DropdownButton title="Actions">
                    <Dropdown.Item>Edit</Dropdown.Item>
                </DropdownButton>
            </td>
        </tr>
    );
}

export function MrpResourcesListRow(props: ComponentProps) {
    const [opened, setOpened] = useState<boolean>(false);

    const mainElement = <MrpResourcesListMainRow 
                                    key={`resource-${props.resource.resourceId}-main-row`}
                                    opened={opened} 
                                    toggleOpened={() => setOpened(!opened)}
                                    resource={props.resource} />
    const items: React.ReactNode[] = [mainElement];

    if (opened) {
        props.resource.consignments.map(c => {
            return (<MrpResourcesListConsignmentRow
                                    key={`resource-${props.resource.resourceId}-${c.consignmentId}`}
                                    consignment={c} />);
        })
        .forEach(i => items.push(i));
    }

    return (
        <>
            {items}
        </>
    );
}