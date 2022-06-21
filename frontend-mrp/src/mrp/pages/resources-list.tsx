import React from 'react';
import './resources/list.css'
import { MrpResourcesListHeader } from './resources/header';
import { MrpResourcesList } from './resources/list';
import { MrpResourcesListToolbar } from './resources/toolbar';
import { Outlet } from 'react-router-dom';

export function MrpResourcesListPage() {
    return (
        <>
            <MrpResourcesListHeader />
            <MrpResourcesListToolbar />
            <MrpResourcesList />

            <Outlet />
        </>
    );
}