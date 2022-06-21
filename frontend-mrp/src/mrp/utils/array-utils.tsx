export function findFirst<T>(input: T[], filter: (item: T) => boolean): T {
    const items = input.filter(item => filter(item));
    if (items.length === 0) {
        throw new Error('No items found');
    }
    if (items.length > 1) {
        throw new Error('More than one item found');
    }
    return items[0];
}

export function hasItem<T>(input: T[], filter: (item: T) => boolean): boolean {
    const items = input.filter(item => filter(item));
    return items.length > 0; 
}

export function replaceFirst<T>(input: T[], value: T, matcher: (item: T) => boolean): T[] {
    const item = findFirst(input, matcher); 
    const index = input.indexOf(item); 
    input[index] = value; 
    return input; 
}