import { LocalDate } from '@js-joda/core';
import { useState } from 'react';
import { PlusCircle, ArrowsCollapse, ArrowsExpand } from 'react-bootstrap-icons';
import { Resource } from '../../model/resource';
import { MrpEditableCell } from './editable-cell';

interface ComponentProps {
    resource: Resource, 
    dateStart: LocalDate, 
    dateEnd: LocalDate
}

const getResourceLeftovers = (resource: Resource, dateStart: LocalDate, dateEnd: LocalDate): React.ReactNode[] => {
    const result: React.ReactNode[] = [];
    let currentDate = dateStart; 
    while (currentDate.isBefore(dateEnd) || currentDate.isEqual(dateEnd)) {
        const amounts: string[] = resource.getLeftovers(currentDate).map(amount => {
            return amount.amount + amount.unit.name;
        })
        const key = `${resource.id} + ${currentDate}`
        result.push(<td key={key}>{amounts.join(', ')}</td>); 

        currentDate = currentDate.plusDays(1);
    }
    return result; 
};

export function MrpResourceRow(props: ComponentProps) {
    const [state, setState] = useState({
        opened: false
    });

    const resourceLeftovers: React.ReactNode[] = getResourceLeftovers(
        props.resource, 
        props.dateStart, 
        props.dateEnd
    ); 

    const daysEmpty: React.ReactNode[] = [];
    for (let i = 1; i < 31; i++) {
        daysEmpty.push(<td>&nbsp;</td>);
    }

    const daysEditable: React.ReactNode[] = [];
    for (let i = 1; i < 31; i++) {
        daysEditable.push(<MrpEditableCell value={10} />);
    }

    const expander = state.opened ? <ArrowsCollapse /> : <ArrowsExpand />
    const toggle = () => { setState({opened: !state.opened}) };
    const consignments = !state.opened ? [] : props.resource.consignments.map(c => {
        return (
        <>
            <tr key={c.id + 'name'}>
                <td>&nbsp;</td>
                <td style={{ width: '48px' }}>&nbsp;</td>
                <td colSpan={3} style={{ borderRight: '2px solid black' }}>{c.name}</td>
                {daysEmpty}
            </tr>
            <tr key={c.id + 'supply'}>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td style={{ width: '48px' }}>&nbsp;</td>
                <td>Supply</td>
                <td style={{ width: '48px', borderRight: '2px solid black' }}>&nbsp;</td>
                {daysEditable}
            </tr>
            <tr key={c.id + 'consumption'}>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td style={{ width: '48px' }}>&nbsp;</td>
                <td>Consumption</td>
                <td style={{ width: '48px', borderRight: '2px solid black' }}>&nbsp;</td>
                {daysEditable}
            </tr>            
        </>
        );
    });

    return (
        <>
            <tr key={props.resource.id}>
                <td>
                    <button className='btn btn-sm' onClick={toggle}>
                        {expander}
                    </button>
                </td>
                <td colSpan={3}>
                    {props.resource.name}
                </td>
                <td style={{ borderRight: '2px solid black' }}>
                    <button className='btn'>
                        <PlusCircle />
                    </button>
                </td>
                {resourceLeftovers}
            </tr>
            {consignments}
        </>
    );   
}