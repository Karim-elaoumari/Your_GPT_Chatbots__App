import { createFeatureSelector, createSelector } from "@ngrx/store";
import { DataState } from "./state";

const getData = (state: DataState) => state.data;
const getLoadingStatus = (state: DataState) => state.loading;
const getError = (state: DataState) => state.error;
const getDataState = createFeatureSelector<DataState>('data');
export const getDataSelector = createSelector(getDataState, getData);
export const getDataLoadingStatusSelector = createSelector(getDataState, getLoadingStatus);
export const getDataErrorSelector = createSelector(getDataState, getError);
