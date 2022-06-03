import { useState } from 'react';
import { PencilFill } from 'react-bootstrap-icons';

interface Props {
    value: number; 
}

export const MrpEditableCell = (props: Props) => {
    const [showPencil, setPencil] = useState(false);

    const pencilShow = () => setPencil(true);
    const pencilHide = () => setPencil(false);

    if (showPencil) {
        return (
            <td onMouseLeave={pencilHide} className='data-cell'>
                <button className='btn'>
                    <PencilFill />
                </button>
            </td>
        );
    }

    return (
        <td onMouseEnter={pencilShow} className='data-cell'>{props.value}</td>
    );
}