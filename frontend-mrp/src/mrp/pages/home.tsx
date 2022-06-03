import React from 'react';
import './home/home.css'
import { MrpHomeHeader } from './home/header';
import { MrpHomeResources } from './home/resources';

export function MrpHomePage() {
    return (
        <>
            <MrpHomeHeader />
            <MrpHomeResources />
        </>
    );
}