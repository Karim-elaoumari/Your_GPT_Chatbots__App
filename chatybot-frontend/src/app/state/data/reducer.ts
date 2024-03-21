
import { DataActionTypes } from './action';
import { DataState, LoadingStatus, initialState } from './state';

// Define your initial state

// Define your reducer function
export function dataReducer(state:DataState = initialState, action:any):DataState  {
    switch (action.type) {
        case DataActionTypes.LOAD_DATA:
            return {
                ...state,
                loading: LoadingStatus.LOADING,
            }
        case DataActionTypes.LOAD_DATA_SUCCESS:
            return {
                ...state,
                loading: LoadingStatus.LOADED,
                data: action.payload
            }
        case DataActionTypes.LOAD_DATA_FAILURE:
            return {
                ...state,
                loading: LoadingStatus.ERROR,
                error: action.payload
            }
        case DataActionTypes.CREATE_DATA:
            return {
                ...state,
                saving: LoadingStatus.LOADING,
            }
        case DataActionTypes.CREATE_DATA_SUCCESS:
            return {
                ...state,
                saving: LoadingStatus.LOADED,
                data: [...state.data, action.payload]
            }
        case DataActionTypes.CREATE_DATA_FAILURE:
            return {
                ...state,
                saving: LoadingStatus.ERROR,
                error: action.payload
            }
        case DataActionTypes.DELETE_DATA:
            return {
                ...state,
                deleting: LoadingStatus.LOADING,
            }
        case DataActionTypes.DELETE_DATA_SUCCESS:
            return {
                ...state,
                deleting: LoadingStatus.LOADED,
                data: state.data.filter((data) => data.id !== action.payload)
            }
        case DataActionTypes.DELETE_DATA_FAILURE:
            return {
                ...state,
                deleting: LoadingStatus.ERROR,
                error: action.payload
            }
        case DataActionTypes.CREATE_FILE_DATA:
            return {
                ...state,
                saving: LoadingStatus.LOADING,
            }
        case DataActionTypes.CREATE_FILE_DATA_SUCCESS:
            return {
                ...state,
                saving: LoadingStatus.LOADED,
                data: [...state.data, action.payload]
            }
        case DataActionTypes.CREATE_FILE_DATA_FAILURE:
            return {
                ...state,
                saving: LoadingStatus.ERROR,
                error: action.payload
            }
        // conversation
        case DataActionTypes.LOAD_CONVERSATIONS:
            return {
                ...state,
                conv_loading: LoadingStatus.LOADING,
            }
        case DataActionTypes.LOAD_CONVERSATIONS_SUCCESS:
            return {
                ...state,
                conv_loading: LoadingStatus.LOADED,
                conversations: action.payload
            }
        case DataActionTypes.LOAD_CONVERSATIONS_FAILURE:
            return {
                ...state,
                conv_loading: LoadingStatus.ERROR,
                conv_error: action.payload
            }
        default:
            return state;
    }
};