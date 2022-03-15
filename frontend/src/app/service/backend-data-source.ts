import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { BehaviorSubject } from "rxjs";
import { Observable } from "rxjs";
import { ProvidesFindAll } from "./provides-find-all";

export class BackendDataSource<T> implements DataSource<T> {
    private provider: BehaviorSubject<T[]> = new BehaviorSubject<T[]>([]);

    constructor(private service: ProvidesFindAll<T>) {

    }

    connect(collectionViewer: CollectionViewer): Observable<readonly T[]> {
        return this.provider;
    }

    disconnect(collectionViewer: CollectionViewer): void {
        
    }

    public refresh(): void {
        this.service.findAll().subscribe(items => {
            this.provider.next(items);
        })
    }
}