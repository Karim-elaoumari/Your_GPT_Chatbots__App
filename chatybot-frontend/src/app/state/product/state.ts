import { Product } from "src/app/core/models/Product";
export enum LoadingStatus {
    LOADING = 'loading',
    LOADED = 'loaded',
    ERROR = 'error',
    INITIAL = 'initial'
}

export interface ProductState {
    products: Product[];
    error: string;
    loading: LoadingStatus;
    product : Product;
}
export const initialState: ProductState = {
    products: [],
    error: '',
    loading: LoadingStatus.INITIAL,
    product: {} as Product
}