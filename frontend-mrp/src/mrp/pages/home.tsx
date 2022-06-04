import React from 'react';
import './home/home.css'
import { MrpHomeHeader } from './home/header';
import { MrpHomeResources } from './home/resources';
import { Outlet } from 'react-router-dom';

export function MrpHomePage() {
    return (
        <>
            <MrpHomeHeader />
            <MrpHomeResources />
            <Outlet />
        </>
    );
}