
import { LoadingStatus, ProductState, initialState } from "./state";
import { ProductActionTypes } from "./action";

export function productsReducer(state: ProductState = initialState, action:any):ProductState {
    switch (action.type) {
        case ProductActionTypes.LOAD_PRODUCTS:
            return {
                ...state,
                loading: LoadingStatus.LOADING
            }
        case ProductActionTypes.LOAD_PRODUCTS_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                products: action.payload
            }   
        case ProductActionTypes.LOAD_PRODUCTS_ITEM_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                products: [...state.products, action.payload]
            }    
        case ProductActionTypes.LOAD_PRODUCTS_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
         case ProductActionTypes.CREATE_PRODUCT_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                products: [...state.products, action.payload]
            }
        case ProductActionTypes.CREATE_PRODUCT_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }

        default:
            return state;
    }
}
