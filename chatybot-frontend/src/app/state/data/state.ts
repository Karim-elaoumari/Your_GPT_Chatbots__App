import { BotData } from "src/app/core/models/BotData";



export enum LoadingStatus {
    LOADING = 'loading',
    LOADED = 'loaded',
    ERROR = 'error',
    INITIAL = 'initial'
}
export interface DataState {
    data: BotData[];
    error: string;
    loading: LoadingStatus;
}
export const initialState: DataState = {
    data: [],
    error: '',
    loading: LoadingStatus.INITIAL

}