export interface Consignment {
    id: number; 
    name: string; 
}

export interface Resource {
    id: number; 
    name: string; 
    consignments: Consignment[];
}

export const data: Resource[] = [
    {
        id: 1, 
        name: 'Морковь', 
        consignments: [
            {
                id: 10, 
                name: 'В пакете'
            },
            {
                id: 11, 
                name: 'Тертая'
            }
        ]
    },
    {
        id: 2, 
        name: 'Картошка', 
        consignments: []
    },
    {
        id: 3, 
        name: 'Коржачий корм', 
        consignments: [
            {
                id: 21, 
                name: 'С рыбой'
            },
            {
                id: 22, 
                name: 'С олениной'
            }
        ]
    }
]