import React from 'react';
import { 
    Route, 
    BrowserRouter as Router, 
    Routes} from 'react-router-dom';
import { MrpNavigation } from './navigation';
import { MrpNotFoundPage } from './pages/404';
import { MrpHomePage } from './pages/home';
import { Container } from 'react-bootstrap';
import { MrpDialogEditConsumption } from './pages/home/dialog-edit-consumption';

export function MrpLayout() {
    return (
        <Router>
            <MrpNavigation></MrpNavigation>

            <Container fluid>
                <Routes>
                    <Route path="/" element={ <MrpHomePage/> }>
                        <Route 
                                path="consignments/:consignmentId/records/:recordDate/consumptions" 
                                element={ <MrpDialogEditConsumption /> } />
                    </Route>
                    <Route path='*' element={ <MrpNotFoundPage/> } />
                </Routes>
            </Container>
        </Router>
    );
}