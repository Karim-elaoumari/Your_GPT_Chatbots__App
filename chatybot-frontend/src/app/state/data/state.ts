import { BotData } from "src/app/core/models/BotData";
import { Conversation } from "src/app/core/models/Conversation";



export enum LoadingStatus {
    LOADING = 'loading',
    LOADED = 'loaded',
    ERROR = 'error',
    INITIAL = 'initial'
}
export interface DataState {
    data: BotData[];
    conversations: Conversation[];
    error: string;
    conv_error: string;
    conv_loading: LoadingStatus;
    loading: LoadingStatus;
    saving: LoadingStatus;
    deleting: LoadingStatus;
}
export const initialState: DataState = {
    data: [],
    error: '',
    loading: LoadingStatus.INITIAL,
    conversations: [],
    conv_error: '',
    saving: LoadingStatus.INITIAL,
    deleting: LoadingStatus.INITIAL,
    conv_loading: LoadingStatus.INITIAL

}