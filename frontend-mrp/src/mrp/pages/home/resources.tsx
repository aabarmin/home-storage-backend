import React, { useState } from 'react';
import { PlusCircle } from 'react-bootstrap-icons';
import { MrpDialogResourceAdd } from './dialog-resource-add';
import { data, Resource } from './dummy-data'
import { MrpResourceRow } from './resource-row';

export function MrpHomeResources() {
    const days = [];
    for (let i = 1; i < 31; i++) {
        days.push(<th key={i}>{i} May</th>);
    }

    const [showDialog, setShowDialog] = useState(false);
    const dialog = showDialog ? <MrpDialogResourceAdd /> : null; 
    const toggleDialog = () => setShowDialog(!showDialog);

    return (
        <div className='row'>
            <div className='col-12'>
                {dialog}
                <table className='table table-bordered'>
                    <thead>
                        <tr>
                            <th style={{ width: '48px' }}>
                                &nbsp;
                            </th>
                            <th style={{ width: '200px' }} colSpan={3}>
                                Resources
                            </th>
                            <th style={{ width: '48px' }}>
                                <button className='btn' onClick={toggleDialog}>
                                    <PlusCircle />
                                </button>
                            </th>
                            {days}
                        </tr>                        
                    </thead>
                    <tbody>
                        {data.map((r: Resource) => {
                            return <MrpResourceRow resource={r} key={r.id} />
                        })}
                    </tbody>
                </table>

            </div>
        </div>
    );
}