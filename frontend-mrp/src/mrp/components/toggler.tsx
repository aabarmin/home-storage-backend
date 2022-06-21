import React from 'react';
import { ArrowsCollapse, ArrowsExpand } from 'react-bootstrap-icons';

interface ComponentProps {
    opened: boolean, 
    onToggle: () => void
}

export function MrpToggler(props: ComponentProps) {
    const icon = props.opened ? <ArrowsCollapse /> : <ArrowsExpand />
    return (
        <button className='btn btn-sm' onClick={props.onToggle}>
            {icon}
        </button>
    );
}