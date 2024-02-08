import { ex } from "@fullcalendar/core/internal-common";
import { Action } from "@ngrx/store";
import { Product } from "src/app/core/models/Product";

export enum ProductActionTypes {
    LOAD_PRODUCTS = '[Product] Load Products',
    LOAD_PRODUCTS_SUCCESS = '[Product] Load Products Success',
    LOAD_PRODUCTS_ITEM_SUCCESS = '[Product] Load Products Item',
    LOAD_PRODUCTS_FAILURE = '[Product] Load Products Failure',
    LOAD_PRODUCT = '[Product] Load Product',
    LOAD_PRODUCT_SUCCESS = '[Product] Load Product Success',
    LOAD_PRODUCT_FAILURE = '[Product] Load Product Failure',
    CREATE_PRODUCT = '[Product] Create Product',
    CREATE_PRODUCT_SUCCESS = '[Product] Create Product Success',
    CREATE_PRODUCT_FAILURE = '[Product] Create Product Failure',
    UPDATE_PRODUCT = '[Product] Update Product',
    UPDATE_PRODUCT_SUCCESS = '[Product] Update Product Success',
    UPDATE_PRODUCT_FAILURE = '[Product] Update Product Failure',
    DELETE_PRODUCT = '[Product] Delete Product',
    DELETE_PRODUCT_SUCCESS = '[Product] Delete Product Success',
    DELETE_PRODUCT_FAILURE = '[Product] Delete Product Failure',
}
export class LoadProducts implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCTS;
    constructor(public payload?: any) {
    }
}
export class LoadProductsSuccess implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCTS_SUCCESS;
    constructor(public payload: Product[]) {
    }
}
export class LoadProductsItemSuccess implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCTS_ITEM_SUCCESS;
    constructor(public payload: Product) {
    }
}
export class LoadProductsFailure implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCTS_FAILURE;
    constructor(public payload: string) {
    }
}
export class LoadProduct implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCT;
    constructor(public payload: any) {
    }
}
export class LoadProductSuccess implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCT_SUCCESS;
    constructor(public payload: Product) {
    }
}
export class LoadProductFailure implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.LOAD_PRODUCT_FAILURE;
    constructor(public payload: string) {
    }
}
export class CreateProduct implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.CREATE_PRODUCT;
    constructor(public payload: any) {
    }
}
export class CreateProductSuccess implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.CREATE_PRODUCT_SUCCESS;
    constructor(public payload: string) {
    }
}
export class CreateProductFailure implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.CREATE_PRODUCT_FAILURE;
    constructor(public payload: string) {
    }
}
export class UpdateProduct implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.UPDATE_PRODUCT;
    constructor(public payload: any) {
    }
}
export class UpdateProductSuccess implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.UPDATE_PRODUCT_SUCCESS;
    constructor(public payload: string) {
    }
}
export class UpdateProductFailure implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.UPDATE_PRODUCT_FAILURE;
    constructor(public payload: string) {
    }
}
export class DeleteProduct implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.DELETE_PRODUCT;
    constructor(public payload: any) {
    }
}
export class DeleteProductSuccess implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.DELETE_PRODUCT_SUCCESS;
    constructor(public payload: Product[]) {
    }
}
export class DeleteProductFailure implements Action {
    readonly type: ProductActionTypes  = ProductActionTypes.DELETE_PRODUCT_FAILURE;
    constructor(public payload: string) {
    }
}
export type ProductActions = LoadProducts | LoadProductsSuccess | LoadProductsFailure | LoadProduct | LoadProductSuccess | LoadProductFailure | CreateProduct | CreateProductSuccess | CreateProductFailure | UpdateProduct | UpdateProductSuccess | UpdateProductFailure | DeleteProduct | DeleteProductSuccess | DeleteProductFailure;
