import React from 'react';
import { 
    Route, 
    BrowserRouter as Router, 
    Routes} from 'react-router-dom';
import { MrpNavigation } from './navigation';
import { MrpNotFoundPage } from './pages/404';
import { MrpHomePage } from './pages/home';

export function MrpLayout() {
    return (
        <Router>
            <MrpNavigation></MrpNavigation>

            <div className='container-fluid'>
                <Routes>
                    <Route path="/" element={ <MrpHomePage/> } />
                    <Route path='*' element={ <MrpNotFoundPage/> } />
                </Routes>
            </div>
        </Router>
    );
}