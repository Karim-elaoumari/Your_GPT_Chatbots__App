import { createFeatureSelector, createSelector } from "@ngrx/store";
import { ProductState } from "./state";
const getProducts = (state: ProductState) => state.products;
const getProductsLoading = (state: ProductState) => state.loading;
const getProductsError = (state: ProductState) => state.error;
const getProductsState = createFeatureSelector<ProductState>('products');
export const getProductsSelector = createSelector(getProductsState, getProducts);
export const getProductsLoadingSelector = createSelector(getProductsState, getProductsLoading);
export const getProductsErrorSelector = createSelector(getProductsState, getProductsError);