import React from 'react';
import './resources/list.css'
import { MrpResourcesListHeader } from './resources/header';
import { MrpResourcesList } from './resources/list';
import { MrpResourcesListToolbar } from './resources/toolbar';

export function MrpResourcesListPage() {
    return (
        <>
            <MrpResourcesListHeader />
            <MrpResourcesListToolbar />
            <MrpResourcesList />
        </>
    );
}