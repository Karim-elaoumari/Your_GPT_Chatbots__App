import { ChatbotState } from "./chatbot/state";
import { DataState } from "./data/state";

export interface RootState{
    chatbots: ChatbotState;
    data: DataState;
}