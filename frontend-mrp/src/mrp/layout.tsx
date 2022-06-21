import React from 'react';
import { 
    Route, 
    BrowserRouter as Router, 
    Routes,
    Navigate} from 'react-router-dom';
import { MrpNavigation } from './navigation';
import { MrpNotFoundPage } from './pages/404';
import { MrpHomePage } from './pages/home';
import { Container } from 'react-bootstrap';
import { MrpDialogEditConsumption } from './pages/home/dialog/dialog-edit-consumption';
import { MrpDialogEditSupply } from './pages/home/dialog/dialog-edit-supply';
import { MrpResourcesListPage } from './pages/resources-list';
import { MrpDialogCreateResource } from './pages/resources/dialog/dialog-create-resource';

export function MrpLayout() {
    return (
        <Router>
            <MrpNavigation></MrpNavigation>

            <Container fluid>
                <Routes>
                    <Route path="/" element={ <Navigate to="/mrp" /> } />

                    <Route path="/mrp" element={ <MrpHomePage/> }>
                        <Route 
                                path="consignments/:consignmentId/records/:recordDate/consumptions" 
                                element={ <MrpDialogEditConsumption /> } />
                        <Route 
                                path="consignments/:consignmentId/records/:recordDate/supplies" 
                                element={ <MrpDialogEditSupply /> } />
                    </Route>

                    <Route path="/mrp/list" element={ <MrpResourcesListPage /> }>
                        <Route
                                path="resources/new"
                                element={ <MrpDialogCreateResource /> } />
                    </Route>

                    <Route path='*' element={ <MrpNotFoundPage/> } />
                </Routes>
            </Container>
        </Router>
    );
}