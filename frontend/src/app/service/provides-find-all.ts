import { Observable } from "rxjs";

export interface ProvidesFindAll<T> {
    findAll(): Observable<T[]>;
}