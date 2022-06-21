import { useState } from 'react';
import { PencilFill } from 'react-bootstrap-icons';
import { Link } from 'react-router-dom';

interface Props {
    value: number; 
    link: string;
}

export const MrpEditableLinkCell = (props: Props) => {
    const [showPencil, setPencil] = useState(false);

    const pencilShow = () => setPencil(true);
    const pencilHide = () => setPencil(false);

    if (showPencil) {
        return (
            <td onMouseLeave={pencilHide} className='data-cell'>
                <Link to={props.link} className='btn'>
                    <PencilFill />
                </Link>
            </td>
        );
    }

    return (
        <td onMouseEnter={pencilShow} className='data-cell'>{props.value}</td>
    );
}