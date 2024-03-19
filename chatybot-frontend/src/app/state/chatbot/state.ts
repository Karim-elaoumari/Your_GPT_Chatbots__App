import { ChatBot } from "src/app/core/models/ChatBot";

export enum LoadingStatus {
    LOADING = 'loading',
    LOADED = 'loaded',
    ERROR = 'error',
    INITIAL = 'initial'
}
export interface ChatbotState {
    chatbots: ChatBot[];
    error: string;
    loading: LoadingStatus;
    origins_loading: LoadingStatus;
    chatbot : ChatBot;
    chatbot_data_caracters: number;
}
export const initialState: ChatbotState = {
    chatbots: [],
    error: '',
    loading: LoadingStatus.INITIAL,
    origins_loading: LoadingStatus.INITIAL,
    chatbot: {} as ChatBot,
    chatbot_data_caracters: 0

}