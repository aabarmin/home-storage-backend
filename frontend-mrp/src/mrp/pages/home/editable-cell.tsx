import { useState } from 'react';
import { PencilFill } from 'react-bootstrap-icons';

export interface Props {
    value: number; 
}

export function MrpEditableCell(props: Props) {
    const [editable, setEditable] = useState(false);
    const [showPencil, setPencil] = useState(false);

    const pencilShow = () => setPencil(true);
    const pencilHide = () => setPencil(false);

    const editStart = () => setEditable(true);
    const editEnd = () => setEditable(false);

    if (editable) {
        return (
            <td>
                <input type='text' value={props.value} />
            </td>
        );
    }

    if (showPencil) {
        return (
            <td onMouseLeave={pencilHide}>
                <button className='btn' onClick={editStart}>
                    <PencilFill />
                </button>
            </td>
        );
    }

    return (
        <td onMouseEnter={pencilShow}>{props.value}</td>
    );
}