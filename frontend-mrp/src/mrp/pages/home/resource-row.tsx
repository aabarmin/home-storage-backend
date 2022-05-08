import { useState } from 'react';
import { PlusCircle, ArrowsCollapse, ArrowsExpand } from 'react-bootstrap-icons';
import { MrpEditableCell } from './editable-cell';
import { Resource } from './model/response';

export interface ComponentType {
    resource: Resource
}

export function MrpResourceRow(props: ComponentType) {
    const [state, setState] = useState({
        opened: false
    });

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
                {daysEmpty}
            </tr>
            {consignments}
        </>
    );   
}