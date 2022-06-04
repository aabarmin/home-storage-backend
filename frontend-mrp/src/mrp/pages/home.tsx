import React from 'react';
import './home/home.css'
import { MrpHomeHeader } from './home/header';
import { MrpHomeResources } from './home/resources';
import { Outlet } from 'react-router-dom';
import { MrpHomeDateSelector } from './home/date-selector';

export function MrpHomePage() {
    return (
        <>
            <MrpHomeHeader />
            <MrpHomeDateSelector />
            <MrpHomeResources />
            <Outlet />
        </>
    );
}