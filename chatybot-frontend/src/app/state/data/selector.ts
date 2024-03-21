import { createFeatureSelector, createSelector } from "@ngrx/store";
import { DataState } from "./state";

const getData = (state: DataState) => state.data;
const getLoadingStatus = (state: DataState) => state.loading;
const getError = (state: DataState) => state.error;
// conversation
const getConversations = (state: DataState) => state.conversations;
const getConvLoadingStatus = (state: DataState) => state.conv_loading;
const getConvError = (state: DataState) => state.conv_error;
const getDataState = createFeatureSelector<DataState>('data');
const getSavingStatus = (state: DataState) => state.saving;
const getDeletingStatus = (state: DataState) => state.deleting;
export const getDataSelector = createSelector(getDataState, getData);
export const getDataLoadingStatusSelector = createSelector(getDataState, getLoadingStatus);
export const getDataErrorSelector = createSelector(getDataState, getError);
export const getDataSavingStatusSelector = createSelector(getDataState, getSavingStatus);
export const getDataDeletingStatusSelector = createSelector(getDataState, getDeletingStatus);
// conversation
export const getConversationsSelector = createSelector(getDataState, getConversations);
export const getConvLoadingStatusSelector = createSelector(getDataState, getConvLoadingStatus);
export const getConvErrorSelector = createSelector(getDataState, getConvError);
