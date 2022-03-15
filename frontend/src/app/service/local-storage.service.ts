export class LocalStorageService<T> {
    public getStorage(key: string): T[] {
        if (!localStorage.getItem(key)) {
            return [];
        }
        const valueString = String(localStorage.getItem(key));
        return JSON.parse(valueString) as T[];
    }

    public setStorage(key: string, value: T[]): void {
        const valueString = JSON.stringify(value);
        localStorage.setItem(key, valueString);
    }
}